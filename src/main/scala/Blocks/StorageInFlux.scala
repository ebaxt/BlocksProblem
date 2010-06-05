package Blocks

final class StorageInFlux(val storage: Storage, val boxesInTransit: Column) {
	def onto(box: Box): Storage = null
	def over(box: Box): Storage = null

	override def equals(o: Any) = o match {
		case other: StorageInFlux => other.storage == storage &&
			other.boxesInTransit == boxesInTransit
		case _ => false
	}

	override def hashCode = storage.hashCode * 31 + boxesInTransit.hashCode
}
