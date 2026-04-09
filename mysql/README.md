# MySQL Topic Practice

This workspace now contains separate SQL files for important MySQL topics.

## Topic Files

- `topics/01_basic_select_queries.sql`
- `topics/02_joins.sql`
- `topics/03_subqueries.sql`
- `topics/04_stored_procedures.sql`
- `topics/05_triggers.sql`
- `topics/06_aggregate_functions_group_by.sql`
- `topics/07_constraints_keys.sql`
- `topics/08_views_indexes.sql`
- `topics/09_transactions.sql`
- `topics/10_case_string_date_functions.sql`

## Daily Topic Generator

Run this command from the project root to create one new dated practice file:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\new-mysql-topic.ps1
```

Daily files will be created in `daily-topics/`.
