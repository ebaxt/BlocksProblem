package Blocks

import org.scalatest.junit.{ShouldMatchersForJUnit}
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import junit.framework.Assert._

@RunWith(classOf[JUnit4])
class StorageTest {
  @Test def shouldInitializeColumns() {
    val list = List(Column("a"), Column("b"))
    val storage = new Storage(list)
    assertEquals(list, storage.columns)
  }

  @Test def shouldEqualOnColumns() {
    assertEquals(new Storage(List(Column("a"))), new Storage(List(Column("a"))))
    assertEquals((new Storage(List(Column("a")))).hashCode, (new Storage(List(Column("a")))).hashCode)
    assertFalse((new Storage(List(Column("a")))).equals(new Storage(List(Column("f")))))
  }

  @Test def factoryShouldPopulateStorage() {
    assertEquals(new Storage(List(Column("a"))), Storage(Column("a")))
    assertEquals(new Storage(List(Column("a"), Column("b", "c"))), Storage(Column("a"), Column("b", "c")))
  }

  @Test def factoryShouldPopulateStorageFromStringArgs() {
    assertEquals(Storage(Column("a")), Storage(List("a")))
    assertEquals(Storage(Column("a"), Column("b"), Column("c")), Storage(List("a", "b", "c")))
  }

	@Test def testMoveTopFromColumn() {
		assertEquals(new StorageInFlux(Storage(Column(), Column("b", "c")), Column("a")),
			Storage(Column("a"), Column("b", "c")).move(new Box("a")))
		assertEquals(new StorageInFlux(Storage(Column(), Column("b", "c")), Column("d")),
			Storage(Column("d"), Column("b", "c")).move(new Box("d")))
		assertEquals(new StorageInFlux(Storage(Column("a"), Column("b", "c")), Column("d")),
			Storage(Column("d", "a"), Column("b", "c")).move(new Box("d")))
		assertEquals(new StorageInFlux(Storage(Column("d", "a"), Column("c")), Column("b")),
			Storage(Column("d", "a"), Column("b", "c")).move(new Box("b")))
	}

	@Test def testMoveNonTopFromColumn() {
		assertEquals(new StorageInFlux(Storage(Column("d", "a"), Column("b")), Column("c")),
			Storage(Column("d", "a"), Column("b", "c")).move(new Box("c")))
		assertEquals(new StorageInFlux(Storage(Column("d"), Column("b", "c")), Column("a")),
			Storage(Column("d", "a"), Column("b", "c")).move(new Box("a")))
	}

	@Test def testPileTopFromColumn() {
		assertEquals(new StorageInFlux(Storage(Column(), Column("b", "c")), Column("a")),
			Storage(Column("a"), Column("b", "c")).pile(new Box("a")))
		assertEquals(new StorageInFlux(Storage(Column(), Column("b", "c")), Column("d")),
			Storage(Column("d"), Column("b", "c")).pile(new Box("d")))
		assertEquals(new StorageInFlux(Storage(Column("a"), Column("b", "c")), Column("d")),
			Storage(Column("d", "a"), Column("b", "c")).pile(new Box("d")))
		assertEquals(new StorageInFlux(Storage(Column("d", "a"), Column("c")), Column("b")),
			Storage(Column("d", "a"), Column("b", "c")).pile(new Box("b")))
	}

	@Test def testPileNonTopFromColumn() {
		assertEquals(Storage(Column("d", "a"), Column()),
			Storage(Column("d", "a"), Column("b", "c")).pile(new Box("c")).storage)
		assertEquals(new StorageInFlux(Storage(Column("d", "a"), Column()), Column("c", "b")),
			Storage(Column("d", "a"), Column("b", "c")).pile(new Box("c")))
		assertEquals(new StorageInFlux(Storage(Column("d"), Column("b", "c")), Column("a")),
			Storage(Column("d", "a"), Column("b", "c")).move(new Box("a")))
	}
}
