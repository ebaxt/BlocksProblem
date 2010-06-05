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
	type Action2 = (Box, Column) => (Column, Column)

	def applyToMatchingColumns(box: Box, action: Action) =
		new Storage(columns.map(
			c => if (c.contains(box)) action(box, c) else c))

	private def topTo(b: Box, source: Column, target: Column)
	: Column = if (source.isEmpty) throw new IllegalStateException()
	else {
		if (source.top == b) {
			target.push(source.top)
		} else {
			topTo(b, source.pop, target.push(source.top))
		}
	}

	private def pile(box: Box, source: Column): Column = {
		val unstacked = unstack(box, source, Column())
		unstacked._1
	}

	private def move(box: Box, source: Column): Column = {
		val unstacked = unstack(box, source, Column())
		(unstacked._1.pushAll(unstacked._2))
	}

	/**
	 * Unstacks elements until it encounters box. Returns a tuple with the elements remaining on the stack
	 * and elements taken from the stack (excluding box)
	 */
	private def unstack(box: Box, source: Column, target: Column): (Column, Column) = if (source.isEmpty) {
		throw new IllegalStateException()
	} else {
		if (box == source.top) (source.pop, target)
		else unstack(box, source.pop, target.push(source.top))
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
