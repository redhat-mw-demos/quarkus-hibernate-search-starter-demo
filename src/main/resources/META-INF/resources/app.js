export default {
    data() {
        return {
            search: {
                input: {
                }
            },
            modals: {
                product: {
                    model: {
                    }
                },
                variant: {
                    model: {
                    }
                }
            },
            constants: {
                department: {
                    'BOOKS': {label: 'Books', icon: 'book'},
                    'ELECTRONICS': {label: 'Electronics', icon: 'laptop'},
                    'FASHION': {label: 'Fashion', icon: 'tshirt'},
                    'HOME': {label: 'Home', icon: 'home'},
                    'SPORTS': {label: 'Sports', icon: 'biking'},
                    'TOOLS': {label: 'Tools', icon: 'tools'}
                },
                priceRange: {
                  '_0_5': {label: 'Under $5'},
                  '_5_10': {label: '$5 to $10'},
                  '_10_20': {label: '$10 to $20'},
                  '_20_50': {label: '$20 to $50'},
                  '_50_100': {label: '$50 to $100'},
                  '_100_PLUS': {label: 'Over $100'}
                }
            }
        }
    },
    watch: {
        'search.input': {
            handler(newValue, oldValue) {
                this.doSearch()
            },
            deep: true,
            immediate: true
        }
    },
    computed: {
        // This makes sure we only run search ~300ms after the user is done typing the text
        // See https://stackoverflow.com/a/53486112/6692043
        debouncingSearchInputText: {
            get() {
                return this.search.input.text
            },
            set(val) {
                if (this.timeout) clearTimeout(this.timeout)
                this.timeout = setTimeout(() => {
                    this.search.input.text = val
                }, 300)
            }
        }
    },
    methods: {
        async showProductModal(id) {
            this.modals.product.id = id
            $('#productModal .ui.form').form('clear')
            if (id) {
                this.modals.product.model = await this.jsonFetch('GET', './product/' + id)
                // Vuejs doesn't update the dropdown for some reason, so we do that manually.
                $('#productModal .dropdown.department')
                    .dropdown('set selected', this.modals.product.model.department)
                ;
            }
            else {
                this.modals.product.model = {}
            }
            $('#productModal').modal('show')
        },
        async submitProduct(id) {
            if (!$('#productModal .ui.form').form('validate form')) {
                return;
            }
            if (this.modals.product.id) {
                await this.jsonDo('PUT', './product/' + this.modals.product.id, this.modals.product.model)
                this.success('The product was updated.')
            }
            else {
                await this.jsonDo('POST', './product/', this.modals.product.model)
                this.success('The product was created.')
            }
            $('#productModal').modal('hide')
            this.doSearch()
        },
        async deleteProduct(id) {
            await this.jsonDo('DELETE', './product/' + id)
            this.success('The product was deleted.')
            this.doSearch()
        },
        async showVariantModal(productId, id) {
            this.modals.variant.productId = productId
            this.modals.variant.id = id
            $('#variantModal .ui.form').form('clear')
            if (id) {
                this.modals.variant.model = await this.jsonFetch('GET', './product/variant/' + id)
            }
            else {
                this.modals.variant.model = {}
            }
            $('#variantModal').modal('show')
        },
        async submitVariant(id) {
            if (!$('#variantModal .ui.form').form('validate form')) {
                return;
            }
            if (this.modals.variant.id) {
                await this.jsonDo('PUT', './product/variant/' + this.modals.variant.id, this.modals.variant.model)
                this.success('The variant was updated.')
            }
            else {
                await this.jsonDo('POST', './product/' + this.modals.variant.productId + '/variant',
                        this.modals.variant.model)
                this.success('The variant was created.')
            }
            $('#variantModal').modal('hide')
            this.doSearch()
        },
        async deleteVariant(id) {
            await this.jsonDo('DELETE', './product/variant/' + id)
            this.success('The variant was deleted.')
            this.doSearch()
        },
        async doSearch() {
            this.search.result = null
            this.search.error = null
            this.search.result = await this.jsonFetch('POST', './product/search', this.search.input)
        },
        async jsonFetch(method, path, body) {
            const res = await this.jsonDo(method, path, body)
            return await res.json()
        },
        async jsonDo(method, path, body) {
            const res = await fetch(path, {
                    method: method,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: body ? JSON.stringify(body) : null
            })
            if (res.ok) {
                return res
            }
            else {
                throw 'Response status is ' + res.status + '; response: ' + await res.text()
            }
        },
        success(message) {
            $('body')
                .toast({
                    class: 'success',
                    title: 'Operation successful',
                    message: message
                })
        }
    },
    mounted() {
        $('.dropdown.department')
            .dropdown({
                clearable: true,
                placeholder: 'Department',
                values: Object.entries(this.constants.department)
                    .map(([key, department]) => { return {"name": department.label, "value": key, "icon": department.icon} })
            })
        ;
        $('.ui.form')
            .form({
                autoCheckRequired: true,
                fields: {
                    department: 'empty',
                    price: ['decimal']
                }
            })
        ;
    }
}