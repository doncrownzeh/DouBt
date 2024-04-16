package postgres

import cats.effect.IO
import common.Config.Schema
import common.interface.DatabaseConnection
import doobie.util.transactor.Transactor.Aux
import doobie.implicits._

case class PostgresConnection(transactor: Aux[IO, Unit], schema: Schema) extends DatabaseConnection {

  override def getTables: IO[List[String]] = ???

  override def getColumns: IO[List[String]] = ???

  override def getConstraints: IO[List[String]] = {
    sql"""
         select * FROM (
 select * from (
    select
        pgc.contype as constraint_type,
        ccu.table_schema as table_schema,
        kcu.table_name as table_name,
        case when (pgc.contype = 'f') then kcu.column_name else ccu.column_name end as column_name,
        case when (pgc.contype = 'f') then ccu.table_name else (null) end as reference_table,
        case when (pgc.contype = 'f') then ccu.column_name else (null) end as reference_col,
        case when (pgc.contype = 'p') then 'yes' else 'no' end as auto_inc,
        case when (pgc.contype = 'p') then 'no' else 'yes' end as is_nullable,
        'integer' as data_type,
        '0' as numeric_scale,
        '32' as numeric_precision
    from
        pg_constraint as pgc
        join pg_namespace nsp on nsp.oid = pgc.connamespace
        join pg_class cls on pgc.conrelid = cls.oid
        join information_schema.key_column_usage kcu on kcu.constraint_name = pgc.conname
        left join information_schema.constraint_column_usage ccu on pgc.conname = ccu.constraint_name
        and nsp.nspname = ccu.constraint_schema
     union
        select
            null as constraint_type ,
            table_schema,
            table_name,
            column_name,
            null as refrence_table,
            null as refrence_col,
            'no' as auto_inc,
            is_nullable,
            data_type,
            numeric_scale,
            numeric_precision
        from information_schema.columns cols
        where
            table_schema = 'public'
            and concat(table_name, column_name) not in(
                select concat(kcu.table_name, kcu.column_name)
                from
                pg_constraint as pgc
                join pg_namespace nsp on nsp.oid = pgc.connamespace
                join pg_class cls on pgc.conrelid = cls.oid
                join information_schema.key_column_usage kcu on kcu.constraint_name = pgc.conname
                left join information_schema.constraint_column_usage ccu on pgc.conname = ccu.constraint_name
                and nsp.nspname = ccu.constraint_schema
            )
    ) as foo
order by table_name asc, column_name)
where table_schema = ${schema}""".query[String].to[List].transact(transactor)
  }

  override def getIndices: IO[List[String]] = ???
}
