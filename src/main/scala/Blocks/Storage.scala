package Blocks

final class Storage(val columns: List[Column]) {
	def move(box: Box): StorageInFlux = new StorageInFlux(
		applyToMatchingColumns(box, move), new Column(List(box)))

	def pile(box: Box): StorageInFlux = {
		val str = applyToMatchingColumns(box, pile)
		val bit = columns.find(c => c.contains(box)) match {
			case Some(c) => c.topTo(box)
			case None => throw new IllegalStateException()
		}
		new StorageInFlux(str, bit)
	}

	type Action = (Box, Column) => Column

	def applyToMatchingColumns(box: Box, action: Action) =
		new Storage(columns.map(
			c => if (c.contains(box)) action(box, c) else c))

	private def pile(box: Box, source: Column): Column = {
		source.popTo(box)
	}

	private def move(box: Box, source: Column): Column = {
		val remainder = source.popTo(box)
		val popped = source.topTo(box).pop
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
