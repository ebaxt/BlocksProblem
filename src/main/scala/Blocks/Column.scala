package Blocks

final class Column(val contents: List[Box]) {
	def push(box: Box): Column = new Column(box :: contents) 
	def pop: (Column, Box) = contents match {
		case box :: remainder => (new Column(remainder), box)
		case Nil => throw new IllegalStateException()
	}
	def contains(box: Box): Boolean = contents.contains(box)
	def isEmpty: Boolean = contents.isEmpty

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
