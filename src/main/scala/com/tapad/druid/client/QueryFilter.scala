package com.tapad.druid.client

import org.json4s.JsonAST.{JNull, JArray, JObject, JValue}
import org.json4s.JsonDSL._

sealed trait QueryFilter extends Expression {
  def and(other: QueryFilter) : QueryFilter = And(Seq(this, other))
  def or(other: QueryFilter) : QueryFilter = Or(Seq(this, other))
  def not : QueryFilter = Not(this)
}

case class And(filters: Seq[Expression]) extends QueryFilter {

  override def and(other: QueryFilter): QueryFilter = copy(other +: filters)

  def toJson: JValue = JObject(
    "type" -> "and",
    "fields" -> JArray(filters.toList.map(_.toJson))
  )
}
case class Or(filters: Seq[Expression]) extends QueryFilter {

  override def or(other: QueryFilter): QueryFilter = copy(other +: filters)

  def toJson: JValue = JObject(
    "type" -> "or",
    "fields" -> JArray(filters.toList.map(_.toJson))
  )
}
case class Not(filter: Expression) extends QueryFilter {

  override def not: QueryFilter = copy(filter)

  def toJson: JValue = JObject(
    "type" -> "not",
    "field" -> filter.toJson
  )
}
case class ExprQueryFilter(typeName: String, dimension: String, value: String) extends QueryFilter {
  def toJson: JValue = JObject(
    "type" -> typeName,
    "dimension" -> dimension,
    "value" -> value
  )
}
case class SelectorQueryFilter(dimension: String, value: String) extends QueryFilter {
  def toJson: JValue = JObject(
    "type" -> "selector",
    "dimension" -> dimension,
    "value" -> value
  )
}
case class RegexQueryFilter(dimension: String, pattern: String) extends QueryFilter {
  def toJson: JValue = JObject(
    "type" -> "regex",
    "dimension" -> dimension,
    "pattern" -> pattern
  )
}
case class BoundQueryFilter(dimension: String, lower: String, upper: String) extends QueryFilter {
  def toJson: JValue = JObject(
    "type" -> "bound",
    "dimension" -> dimension,
    "lower" -> lower,
    "upper" -> upper,
    "order" -> "numeric"
  )
}
object QueryFilter {

  def custom(typeName: String, dimension: String, value: String) = ExprQueryFilter(typeName, dimension, value)
  def where(dimension: String, value: String) = SelectorQueryFilter(dimension, value)
  def regex(dimension: String, pattern: String) = RegexQueryFilter(dimension, pattern)
  def bound(dimension: String, lower: String, upper: String) = BoundQueryFilter(dimension, lower, upper)

  val All = new QueryFilter {
    def toJson: JValue = JNull
  }
}
