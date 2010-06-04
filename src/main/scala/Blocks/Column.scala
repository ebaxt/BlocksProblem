package Blocks

final class Column(val contents: List[Box]) {
  override def equals(o: Any) = o match {
    case other: Column => other.contents == contents
    case _ => false
  }

  override def hashCode = contents.hashCode
}

object Column {
  def apply(args: String*) = {
    val list: List[Box] = args.map(p => new Box(p)).toList
    new Column(list)
  }
}