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

	def pile(box: Box): StorageInFlux = {
		val result = pile(box, columns)
		new StorageInFlux(new Storage(result._1), result._2)
	}

	private def pile(box: Box, l0: List[Column]): (List[Column], Column) = l0 match {
		case column :: l1 => if (column.contains(box)) {
				val newColumns = pile(box, column, Column())
				(newColumns._1 :: l1, newColumns._2)
			} else {
				val recurse = pile(box, l1)
				(column :: recurse._1, recurse._2)
			}
		case Nil => throw new IllegalArgumentException()
	}

	private def pile(box: Box, source: Column, target: Column): (Column, Column) = if (source.isEmpty) {
		throw new IllegalStateException()
	} else {
		val pop = source.pop
		if (box == pop._2) (pop._1, target.push(box))
		else pile(box, pop._1, target.push(pop._2))
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
