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

	@Ignore
	@Test def moveOverShouldPlaceOnTop() {

	}

	@Ignore
	@Test def pileOverShouldPlaceOnTop() {

	}

	@Ignore
	@Test def moveOntoShouldPlaceInStack() {

	}

	@Ignore
	@Test def pileOntoShouldPlaceInStack() {

	}
}
