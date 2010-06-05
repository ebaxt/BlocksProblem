package Blocks

final class StorageInFlux(val storage: Storage, val boxesInTransit: Column) {
	def over(box: Box): Storage = new Storage(storage.columns.map(
		c => if (c.contains(box)) c.pushAll(boxesInTransit) else c))

	def onto(box: Box): Storage = new Storage(storage.columns.map(
		c => if (c.contains(box)) onto(c, box) else c))

	private def onto(c: Column, box: Box): Column = {
		val unstacked = unstackInclusive(box, c, Column())
		unstacked._1.pushAll(boxesInTransit).pushAll(unstacked._2)
	}

	override def equals(o: Any) = o match {
		case other: StorageInFlux => other.storage == storage &&
			other.boxesInTransit == boxesInTransit
		case _ => false
	}

	override def hashCode = storage.hashCode * 31 + boxesInTransit.hashCode

	/**
	 * Unstacks elements until it encounters box. Returns a tuple with the elements remaining on the stack
	 * and elements taken from the stack (including box)
	 */
	private def unstackInclusive(box: Box, source: Column, target: Column): (Column, Column) = if (source.isEmpty) {
		throw new IllegalStateException()
	} else {
		if (box == source.top) (source, target)
		else unstackInclusive(box, source.pop, target.push(source.top))
	}
}
