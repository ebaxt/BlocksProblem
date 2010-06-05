package Blocks

final class StorageInFlux(val storage: Storage, val boxesInTransit: Column) {
	def over(box: Box): Storage = new Storage(storage.columns.map(
		c => if (c.contains(box)) c.pushAll(boxesInTransit) else c))

	def onto(box: Box): Storage = null

	override def equals(o: Any) = o match {
		case other: StorageInFlux => other.storage == storage &&
			other.boxesInTransit == boxesInTransit
		case _ => false
	}

	override def hashCode = storage.hashCode * 31 + boxesInTransit.hashCode
}
