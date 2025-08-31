-- Customers
CREATE TABLE customers (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255),
  account_id VARCHAR(128),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- Invoices
CREATE TABLE invoices (
  id BIGSERIAL PRIMARY KEY,
  customer_id BIGINT NOT NULL REFERENCES customers(id),
  amount NUMERIC(19,4) NOT NULL,
  currency VARCHAR(8) NOT NULL DEFAULT 'USD',
  due_date DATE,
  status VARCHAR(32) NOT NULL DEFAULT 'PENDING',
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- Deals
CREATE TABLE deals (
  id BIGSERIAL PRIMARY KEY,
  invoice_id BIGINT NOT NULL REFERENCES invoices(id),
  purchaser_account_id VARCHAR(128) NOT NULL,
  purchase_price NUMERIC(19,4) NOT NULL,
  status VARCHAR(32) NOT NULL DEFAULT 'INITIATED',
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
  transaction_id BIGSERIAL NOT NULL
);

-- Useful indexes
CREATE INDEX idx_invoices_customer ON invoices(customer_id);
CREATE INDEX idx_deals_invoices ON deals(invoice_id);