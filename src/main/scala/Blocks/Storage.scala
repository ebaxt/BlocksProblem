package Blocks

final class Storage(val columns: List[Column]) {
	def move(box: Box): StorageInFlux = new StorageInFlux(
		applyToMatchingColumns(box, move), new Column(List(box)))

	def pile(box: Box): StorageInFlux = {
		val str = applyToMatchingColumns(box, pile)
		val bit = columns.find(c => c.contains(box)) match {
			case Some(c) => topTo(box, c, Column())
			case None => throw new IllegalStateException()
		}
		new StorageInFlux(str, bit)
	}

	type Action = (Box, Column) => Column

	def applyToMatchingColumns(box: Box, action: Action) =
		new Storage(columns.map(
			c => if (c.contains(box)) action(box, c) else c))

	/**
	 * The top of the column, to and including b.
	 */
	private def topTo(b: Box, source: Column, target: Column)
	: Column = if (source.isEmpty) throw new IllegalStateException()
	else {
		if (source.top == b) target.push(source.top)
		else topTo(b, source.pop, target.push(source.top))
	}

	/**
	 * Pop elements from the column, to and including b.
	 */
	private def popTo(b: Box, source: Column)
	: Column = if (source.isEmpty) throw new IllegalStateException()
	else {
		if (source.top == b) source.pop
		else popTo(b, source.pop)
	}

	private def pile(box: Box, source: Column): Column = {
		popTo(box, source)
	}

	private def move(box: Box, source: Column): Column = {
		val remainder = popTo(box, source)
		val popped = topTo(box, source, Column()).pop
		remainder.pushAll(popped)
	}

	override def equals(o: Any) = o match {
		case other: Storage => other.columns == columns
		case _ => false
	}

	override def hashCode = columns.hashCode
}

object Storage {
	def apply(args: Column*): Storage = {
		new Storage(args.toList)
	}

	def apply(boxes: List[String]): Storage = {
		new Storage(boxes.map(b => Column(b)))
	}
}
