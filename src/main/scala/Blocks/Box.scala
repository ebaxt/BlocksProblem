package Blocks

final class Box(val name: String) {
  override def equals(o: Any) = o match {
    case other: Box => name == other.name
    case _ => false
  }


  override def hashCode = name.hashCode
}
