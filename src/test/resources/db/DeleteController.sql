INSERT INTO T_API_KEY(id, name, scopes, expiry_date_time)
    VALUES
        ('1', 'test', 'foo,bar', null),
        ('2', 'test', 'foo,bar', '2040-04-01'),
        ('3', 'test', 'foo,bar', '2019-04-01');
