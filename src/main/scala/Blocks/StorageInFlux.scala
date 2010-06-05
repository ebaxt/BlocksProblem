package Blocks

final class StorageInFlux(val storage: Storage, val boxesInTransit: Column) {
	override def equals(o: Any) = o match {
		case other: StorageInFlux => other.storage == storage &&
			other.boxesInTransit == boxesInTransit
		case _ => false
	}

	override def hashCode = storage.hashCode * 31 + boxesInTransit.hashCode
}
