package Blocks

import org.scalatest.junit.{ShouldMatchersForJUnit}
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import junit.framework.Assert

@RunWith(classOf[JUnit4])
class ColumnTest {
  @Test def shouldInitializeContents() {
    val boxList = List(new Box("a"))
    Assert.assertEquals(boxList, (new Column(boxList)).contents)
  }

  @Test def shouldEqualOnContents() {
    val boxList1 = List(new Box("a"), new Box("b"))
    val boxList2 = List(new Box("a"), new Box("b"), new Box("c"))

    Assert.assertEquals(new Column(boxList1), new Column(boxList1))
    Assert.assertEquals((new Column(boxList1)).hashCode,
      (new Column(boxList1)).hashCode)
    Assert.assertFalse((new Column(boxList1)).equals(
      new Column(boxList2)))
  }

  @Test def factoryShouldCreateColumn() {
    val expected: Column = new Column(List(new Box("a"), new Box("b"), new Box("c")))
    Assert.assertEquals(expected, Column("a", "b", "c"))
  }

}