package org.acme.product.config;

import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;
import org.hibernate.search.engine.backend.analysis.AnalyzerNames;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Dependent
@Named("myAnalysisConfigurer")
public class AnalysisConfigurer implements ElasticsearchAnalysisConfigurer {
    @Override
    public void configure(ElasticsearchAnalysisConfigurationContext context) {
        context.tokenFilter("stemmer_english")
                .type("stemmer")
                .param("language", "english");
        context.analyzer(AnalyzerNames.DEFAULT).custom()
                .tokenizer("standard")
                .tokenFilters("lowercase", "stemmer_english", "asciifolding");
    }
}
