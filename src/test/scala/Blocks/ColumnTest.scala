package Blocks

import org.scalatest.junit.{ShouldMatchersForJUnit}
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import junit.framework.Assert._

@RunWith(classOf[JUnit4])
class ColumnTest {
  @Test def shouldInitializeContents() {
    val boxList = List(new Box("a"))
    assertEquals(boxList, (new Column(boxList)).contents)
  }

  @Test def shouldEqualOnContents() {
    val boxList1 = List(new Box("a"), new Box("b"))
    val boxList2 = List(new Box("a"), new Box("b"), new Box("c"))

    assertEquals(new Column(boxList1), new Column(boxList1))
    assertEquals((new Column(boxList1)).hashCode,
      (new Column(boxList1)).hashCode)
    assertFalse((new Column(boxList1)).equals(
      new Column(boxList2)))
  }

  @Test def factoryShouldCreateColumn() {
    val expected: Column = new Column(List(new Box("a"), new Box("b"), new Box("c")))
    assertEquals(expected, Column("a", "b", "c"))
  }

	@Test def containsShouldCheckContents() {
		assertTrue(Column("a", "b", "c").contains(new Box("a")))
		assertTrue(Column("a", "b", "c").contains(new Box("b")))
		assertFalse(Column("a", "b", "c").contains(new Box("d")))
	}

	@Test def pushShouldPrependContents() {
		assertEquals(Column("c", "a", "b"), Column("a", "b").push(new Box("c")))
		assertEquals(Column("b", "c", "a"), Column("c", "a").push(new Box("b")))
	}

	@Test def popShouldRemoveFromFront() {
		assertEquals((Column("a", "b"), new Box("c")), Column("c", "a", "b").pop)
		assertEquals((Column("c", "a"), new Box("b")), Column("b", "c", "a").pop)
	}

	@Test def isEmptyShouldReflectContents() {
		assertFalse(Column("a", "b").isEmpty)
		assertTrue(Column().isEmpty)
	}
}
