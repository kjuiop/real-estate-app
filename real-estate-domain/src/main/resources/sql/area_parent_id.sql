-- [gungu] area parent_id data set query

-- update area a1
-- set a1.parent_id = (
--     select area_id
--     from (
--              select area_id
--              from area a2
--              where level = 1
--                and name = a1.sido
--          ) as subquery
-- )
-- where 1=1
--   and `level` = 2
--   and parent_id is null
-- ;


-- [dong] area parent_id data set query

-- update area a1
-- set a1.parent_id = (
--     select area_id
--     from (
--              select area_id
--              from area a2
--              where level = 2
--                and sido = a1.sido
--                and name = a1.gungu
--          ) as subquery
-- )
-- where 1=1
--   and `level` = 3
--   and parent_id is null
-- ;

-- [ri] area parent_id data set query

-- update area a1
-- set a1.parent_id = (
--     select area_id
--     from (
--              select area_id
--              from area a2
--              where level = 3
--                and sido = a1.sido
--                and gungu = a1.gungu
--                and name = a1.dong
--          ) as subquery
-- )
-- where 1=1
--   and `level` = 4
--   and parent_id is null
-- ;
