package Blocks

import org.scalatest.junit.{ShouldMatchersForJUnit}
import org.junit.Test
import org.junit.Ignore
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import junit.framework.Assert._

@RunWith(classOf[JUnit4])
class StorageInFluxTest {
	@Test def shouldEqualOnStorageAndBoxes() {
		assertEquals(new StorageInFlux(Storage(Column("a"), Column()), Column("b")), 
			new StorageInFlux(Storage(Column("a"), Column()), Column("b")))
		assertEquals((new StorageInFlux(Storage(Column("a"), Column()), Column("b"))).hashCode, 
			(new StorageInFlux(Storage(Column("a"), Column()), Column("b"))).hashCode)
		assertFalse((new StorageInFlux(Storage(Column("a"), Column()), Column("b"))).equals(
			new StorageInFlux(Storage(Column("a"), Column("c")), Column("b"))))
		assertFalse((new StorageInFlux(Storage(Column("a"), Column()), Column("b"))).equals(
			new StorageInFlux(Storage(Column("a"), Column()), Column("c", "b"))))
	}

	@Test def moveOverShouldPlaceOnTop() {
		assertEquals(Storage(Column("b", "a"), Column()), 
			new StorageInFlux(Storage(Column("a"), Column()), Column("b")).over(new Box("a")))
		assertEquals(Storage(Column("c"), Column("b", "a")), 
			new StorageInFlux(Storage(Column("c"), Column("a")), Column("b")).over(new Box("a")))
	}

	@Test def pileOverShouldPlaceOnTop() {
		assertEquals(Storage(Column("c"), Column("d", "b", "a")), 
			new StorageInFlux(Storage(Column("c"), Column("a")), Column("b", "d")).over(new Box("a")))

	}

	@Ignore
	@Test def moveOntoShouldPlaceInStack() {

	}

	@Ignore
	@Test def pileOntoShouldPlaceInStack() {

	}
}
