package Blocks

final class StorageInFlux(val storage: Storage, val boxesInTransit: Column) {
	def over(box: Box): Storage = storage.applyToMatchingColumns(box, over)

	def onto(box: Box): Storage = storage.applyToMatchingColumns(box, onto)

	private def over(b: Box, c: Column): Column = c.pushAll(boxesInTransit)

	private def onto(box: Box, c: Column): Column = {
		val remainder = c.popTo(box).push(box)
		val popped = c.topTo(box).pop
		remainder.pushAll(boxesInTransit).pushAll(popped)
	}

	override def equals(o: Any) = o match {
		case other: StorageInFlux => other.storage == storage &&
			other.boxesInTransit == boxesInTransit
		case _ => false
	}

	override def hashCode = storage.hashCode * 31 + boxesInTransit.hashCode
}
