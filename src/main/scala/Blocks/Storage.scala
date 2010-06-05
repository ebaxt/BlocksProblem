package Blocks

final class Storage(val columns: List[Column]) {
	def move(box: Box): StorageInFlux = {
		val newCols = move(box, columns)
		new StorageInFlux(new Storage(newCols), new Column(List(box)))
	}

	private def move(box: Box, l0: List[Column]): List[Column] = l0 match {
		case column :: l1 => if (column.contains(box)) {
				val newColumn = move(box, column)
				newColumn :: l1
			} else {
				column :: move(box, l1)
			}
		case Nil => throw new IllegalArgumentException()
	}

	private def move(box: Box, column: Column): Column = if (column.isEmpty) {
		throw new IllegalStateException()
	} else {
		val pop = column.pop
		if (box == pop._2) pop._1
		else move(box, pop._1).push(pop._2)
	}

	def pile(box: Box): StorageInFlux = null

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
