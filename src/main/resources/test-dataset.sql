WITH theproduct AS (
  INSERT INTO product(id, name, description, department) VALUES
  (nextval('hibernate_sequence'), 'Little black dress', 'A timeless evening dress', 'FASHION')
  RETURNING id
)
INSERT INTO productvariant(product_id, id, variants_order, name, price)
    SELECT theproduct.id, thevariants.*
    FROM theproduct, (VALUES
        (nextval('hibernate_sequence'), 0, 'XS - Black', 44.99),
        (nextval('hibernate_sequence'), 1, 'S - Black', 44.99),
        (nextval('hibernate_sequence'), 2, 'M - Black', 44.99),
        (nextval('hibernate_sequence'), 3, 'L - Black', 46.99)
      ) AS thevariants;

WITH theproduct AS (
  INSERT INTO product(id, name, description, department) VALUES
  (nextval('hibernate_sequence'), 'Star Odyssey T-Shirt', 'V-neck T-shirt with Star Odyssey logo', 'FASHION')
  RETURNING id
)
INSERT INTO productvariant(product_id, id, variants_order, name, price)
    SELECT theproduct.id, thevariants.*
    FROM theproduct, (VALUES
        (nextval('hibernate_sequence'), 0, 'XS - Red', 16.99),
        (nextval('hibernate_sequence'), 1, 'S - Red', 16.99),
        (nextval('hibernate_sequence'), 2, 'M - Red', 16.99),
        (nextval('hibernate_sequence'), 3, 'L - Red', 17.99),
        (nextval('hibernate_sequence'), 4, 'XL - Red', 17.99),
        (nextval('hibernate_sequence'), 5, 'XXL - Red', 17.99),
        (nextval('hibernate_sequence'), 6, 'XS - Electric Blue', 16.99),
        (nextval('hibernate_sequence'), 7, 'S - Electric Blue', 16.99),
        (nextval('hibernate_sequence'), 8, 'M - Electric Blue', 17.99),
        (nextval('hibernate_sequence'), 9, 'L - Electric Blue', 17.99),
        (nextval('hibernate_sequence'), 10, 'XL - Electric Blue', 17.99),
        (nextval('hibernate_sequence'), 11, 'XXL - Electric Blue', 17.99)
      ) AS thevariants;

WITH theproduct AS (
  INSERT INTO product(id, name, description, department) VALUES
  (nextval('hibernate_sequence'), 'Budget Electric Power Drill',
        'A budget cordless power drill. Decent battery life (2+ hours). Drill bits not included.', 'TOOLS')
  RETURNING id
)
INSERT INTO productvariant(product_id, id, variants_order, name, price)
    SELECT theproduct.id, thevariants.*
    FROM theproduct, (VALUES
        (nextval('hibernate_sequence'), 0, 'Drill + 1 battery', 29.99),
        (nextval('hibernate_sequence'), 1, 'Drill + 2 batteries', 39.99),
        (nextval('hibernate_sequence'), 2, 'Drill + 4 batteries', 54.99)
      ) AS thevariants;

WITH theproduct AS (
  INSERT INTO product(id, name, description, department) VALUES
  (nextval('hibernate_sequence'), 'Professional Electric Power Drill',
        'A professional cordless power drill. Heavy duty, long battery life (8+ hours). Includes drill bits for wood, concrete and steel.', 'TOOLS')
  RETURNING id
)
INSERT INTO productvariant(product_id, id, variants_order, name, price)
    SELECT theproduct.id, thevariants.*
    FROM theproduct, (VALUES
        (nextval('hibernate_sequence'), 0, 'Drill + 1 battery', 79.99),
        (nextval('hibernate_sequence'), 1, 'Drill + 2 batteries', 99.99),
        (nextval('hibernate_sequence'), 2, 'Drill + 2 batteries + bag', 129.99)
      ) AS thevariants;

WITH theproduct AS (
  INSERT INTO product(id, name, description, department) VALUES
  (nextval('hibernate_sequence'), 'Great Expectations',
        'Novel by Charles Dickens. A classic of English literature.', 'BOOKS')
  RETURNING id
)
INSERT INTO productvariant(product_id, id, variants_order, name, price)
    SELECT theproduct.id, thevariants.*
    FROM theproduct, (VALUES
        (nextval('hibernate_sequence'), 0, 'e-book', 2.99),
        (nextval('hibernate_sequence'), 1, 'Paperback', 3.99),
        (nextval('hibernate_sequence'), 2, 'Hardback', 12.99)
      ) AS thevariants;

WITH theproduct AS (
  INSERT INTO product(id, name, description, department) VALUES
  (nextval('hibernate_sequence'), 'Do Androids Dream of Electric Sheeps?',
        'A novel by Philip K. Dick. Science-Fiction. In post-apocalyptic San Francisco, bounty hunter Rick Deckard is seeking 6 escaped androids.', 'BOOKS')
  RETURNING id
)
INSERT INTO productvariant(product_id, id, variants_order, name, price)
    SELECT theproduct.id, thevariants.*
    FROM theproduct, (VALUES
        (nextval('hibernate_sequence'), 0, 'e-book', 3.99),
        (nextval('hibernate_sequence'), 1, 'PDF', 3.99),
        (nextval('hibernate_sequence'), 2, 'Paperback', 4.99),
        (nextval('hibernate_sequence'), 3, 'Hardback', 7.99)
      ) AS thevariants;

WITH theproduct AS (
  INSERT INTO product(id, name, description, department) VALUES
  (nextval('hibernate_sequence'), 'Robots of Dawn',
        'A novel by Isaac Asimov. Science-Fiction/Crime Fiction. Detective Elijah Baley investigates an unusual crime on outer world Aurora, with the help of an android.', 'BOOKS')
  RETURNING id
)
INSERT INTO productvariant(product_id, id, variants_order, name, price)
    SELECT theproduct.id, thevariants.*
    FROM theproduct, (VALUES
        (nextval('hibernate_sequence'), 0, 'e-book', 2.99),
        (nextval('hibernate_sequence'), 1, 'PDF', 2.99),
        (nextval('hibernate_sequence'), 2, 'Paperback', 3.99),
        (nextval('hibernate_sequence'), 3, 'Hardback', 6.99)
      ) AS thevariants;

WITH theproduct AS (
  INSERT INTO product(id, name, description, department) VALUES
  (nextval('hibernate_sequence'), 'Electricity for Dummies',
        'Hands-on guide to wiring power through your entire house.', 'BOOKS')
  RETURNING id
)
INSERT INTO productvariant(product_id, id, variants_order, name, price)
    SELECT theproduct.id, thevariants.*
    FROM theproduct, (VALUES
        (nextval('hibernate_sequence'), 0, 'e-book', 4.99),
        (nextval('hibernate_sequence'), 1, 'PDF', 4.99),
        (nextval('hibernate_sequence'), 2, 'Paperback', 8.99)
      ) AS thevariants;

WITH theproduct AS (
  INSERT INTO product(id, name, description, department) VALUES
  (nextval('hibernate_sequence'), 'Low-end Android Smartphone', 'Perfect if you just want to make phone calls, send texts and browse internet.', 'ELECTRONICS')
  RETURNING id
)
INSERT INTO productvariant(product_id, id, variants_order, name, price)
    SELECT theproduct.id, thevariants.*
    FROM theproduct, (VALUES
        (nextval('hibernate_sequence'), 0, '8GB storage', 244.99),
        (nextval('hibernate_sequence'), 1, '16GB storage', 284.99)
      ) AS thevariants;

WITH theproduct AS (
  INSERT INTO product(id, name, description, department) VALUES
  (nextval('hibernate_sequence'), 'Premium Android Smartphone', 'On top of providing everything you can expect from a smarthphone, this device will handle smoothly even the most demanding mobile games.', 'ELECTRONICS')
  RETURNING id
)
INSERT INTO productvariant(product_id, id, variants_order, name, price)
    SELECT theproduct.id, thevariants.*
    FROM theproduct, (VALUES
        (nextval('hibernate_sequence'), 0, '128GB storage', 1244.99),
        (nextval('hibernate_sequence'), 1, '256GB storage', 1584.99)
      ) AS thevariants;

WITH theproduct AS (
  INSERT INTO product(id, name, description, department) VALUES
  (nextval('hibernate_sequence'), 'Heavy duty duct tape', 'Sticks to all materials. Resists to outdoor conditions.', 'TOOLS')
  RETURNING id
)
INSERT INTO productvariant(product_id, id, variants_order, name, price)
    SELECT theproduct.id, thevariants.*
    FROM theproduct, (VALUES
        (nextval('hibernate_sequence'), 0, '1 roll', 0.99),
        (nextval('hibernate_sequence'), 1, '10 rolls', 8.99)
      ) AS thevariants;

WITH theproduct AS (
  INSERT INTO product(id, name, description, department) VALUES
  (nextval('hibernate_sequence'), 'No, the truth is not out there',
        'An analysis of the most famous alien abduction hoaxes and other UFO tapes.', 'BOOKS')
  RETURNING id
)
INSERT INTO productvariant(product_id, id, variants_order, name, price)
    SELECT theproduct.id, thevariants.*
    FROM theproduct, (VALUES
        (nextval('hibernate_sequence'), 0, 'e-book', 1.99),
        (nextval('hibernate_sequence'), 1, 'PDF', 2.99),
        (nextval('hibernate_sequence'), 2, 'Paperback', 3.99)
      ) AS thevariants;
