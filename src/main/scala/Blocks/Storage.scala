package Blocks

final class Storage(val columns: List[Column]) {
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