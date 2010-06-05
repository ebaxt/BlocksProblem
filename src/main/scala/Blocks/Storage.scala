package Blocks

final class Storage(val columns: List[Column]) {
	def move(box: Box): StorageInFlux = displace(box, move)

	def pile(box: Box): StorageInFlux = displace(box, pile)

	def displace(box: Box, action: Action)
	: StorageInFlux = {
		val result = displace(box, columns, action)
		new StorageInFlux(new Storage(result._1), result._2)
	}

	private type Action = (Box, Column, Column) => (Column, Column)

	private def displace(box: Box, l0: List[Column], action: Action)
	: (List[Column], Column) = l0 match {
		case column :: l1 => if (column.contains(box)) {
				val newColumns = action(box, column, Column())
				(newColumns._1 :: l1, newColumns._2)
			} else {
				prependColumn(displace(box, l1, action), column)
			}
		case Nil => throw new IllegalArgumentException()
	}

	private def prependColumn(displacement: (List[Column], Column), column: Column)
	: (List[Column], Column) = (column :: displacement._1, displacement._2)

	private def pile(box: Box, source: Column, target: Column): (Column, Column) = if (source.isEmpty) {
		throw new IllegalStateException()
	} else {
		val pop = source.pop
		if (box == pop._2) (pop._1, target.push(box))
		else pile(box, pop._1, target.push(pop._2))
	}

	private def move(box: Box, source: Column, target: Column): (Column, Column) = if (source.isEmpty) {
		throw new IllegalStateException()
	} else {
		val pop = source.pop
		if (box == pop._2) (pop._1, new Column(List(box)))
		else {
			val result = move(box, pop._1, target)
			(result._1.push(pop._2), result._2)
		}
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
