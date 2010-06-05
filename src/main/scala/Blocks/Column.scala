package Blocks

final class Column(val contents: List[Box]) {
	def push(box: Box): Column = new Column(box :: contents) 

	def top: Box = contents match {
		case box :: _ => box
		case Nil => throw new IllegalStateException()
	}

	def pop: Column = contents match {
		case box :: remainder => new Column(remainder)
		case Nil => throw new IllegalStateException()
	}

	/**
	 * The top of the column, to and including box.
	 */
	def topTo(box: Box): Column = new Column(topTo(box, contents, Nil))

	private def topTo(box: Box, source0: List[Box], target: List[Box])
	: List[Box] = source0 match {
		case b :: source1 => if (b == box) b :: target else topTo(box, source1, b :: target)
		case Nil => throw new IllegalStateException()
	}

	/**
	 * Pop elements from the column, to and including box.
	 */
	def popTo(box: Box): Column = new Column(popTo(box, contents))

	private def popTo(box: Box, l0: List[Box]): List[Box] = l0 match {
		case b :: l1 => if (b == box) l1 else popTo(box, l1)
		case Nil => throw new IllegalStateException()
	}

	def contains(box: Box): Boolean = contents.contains(box)

	def isEmpty: Boolean = contents.isEmpty

	def pushAll(source: Column): Column = pushAll(this, source)

	private def pushAll(target: Column, source: Column): Column = 
		if (source.isEmpty) target
		else pushAll(target.push(source.top), source.pop)

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
