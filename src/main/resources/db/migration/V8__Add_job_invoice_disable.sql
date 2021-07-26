CREATE EVENT invoice_disable
    ON SCHEDULE EVERY 1 DAY
    DO
    Update invoice
    set is_active= false
    WHERE paid = false
      AND STR_TO_DATE(creating_date, '%Y-%m-%d %H:%i:%s') < (CURRENT_DATE - 3)
      AND invoice.id > 0;