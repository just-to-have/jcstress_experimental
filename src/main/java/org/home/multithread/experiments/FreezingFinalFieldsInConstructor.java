package org.home.multithread.experiments;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

import java.util.function.Consumer;

public class FreezingFinalFieldsInConstructor {
	@JCStressTest
	@Outcome.Outcomes({
			@Outcome(id = "0, 0", expect = Expect.ACCEPTABLE, desc = "Object wasn't initialized for both Actors."),
			@Outcome(id = {        "0, 1", "0, 2", "0, 3", "0, 4", "0, 5", "0, 6", "0, 7"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Partially initialized object."),
			@Outcome(id = {"1, 0", "1, 1", "1, 2", "1, 3", "1, 4", "1, 5", "1, 6", "1, 7", "1, 8"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Partially initialized object."),
			@Outcome(id = {"2, 0", "2, 1", "2, 2", "2, 3", "2, 4", "2, 5", "2, 6", "2, 7", "2, 8"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Partially initialized object."),
			@Outcome(id = {"3, 0", "3, 1", "3, 2", "3, 3", "3, 4", "3, 5", "3, 6", "3, 7", "3, 8"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Partially initialized object."),
			@Outcome(id = {"4, 0", "4, 1", "4, 2", "4, 3", "4, 4", "4, 5", "4, 6", "4, 7", "4, 8"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Partially initialized object."),
			@Outcome(id = {"5, 0", "5, 1", "5, 2", "5, 3", "5, 4", "5, 5", "5, 6", "5, 7", "5, 8"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Partially initialized object."),
			@Outcome(id = {"6, 0", "6, 1", "6, 2", "6, 3", "6, 4", "6, 5", "6, 6", "6, 7", "6, 8"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Partially initialized object."),
			@Outcome(id = {"7, 0", "7, 1", "7, 2", "7, 3", "7, 4", "7, 5", "7, 6", "7, 7", "7, 8"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Partially initialized object."),
			@Outcome(id = {        "8, 1", "8, 2", "8, 3", "8, 4", "8, 5", "8, 6", "8, 7"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Partially initialized object."),
			@Outcome(id = "0, 8", expect = Expect.ACCEPTABLE, desc = "Object wasn't initialized for first Actor but was initialized for second Actor."),
			@Outcome(id = "8, 0", expect = Expect.ACCEPTABLE, desc = "Object was initialized for first Actor but wasn't initialized for second Actor."),
			@Outcome(id = "8, 8", expect = Expect.ACCEPTABLE, desc = "Object wasn't initialized for both Actors."),
			@Outcome(expect = Expect.FORBIDDEN, desc = "Seeing partially constructed object.")
	})
	@State
	public static class NotFrozenFinalProperties {
		private InnerClass object;

		@Actor
		public void method1() {
			new InnerClass(1);
		}

		private void leaking(InnerClass innerClass) {
			object = innerClass;
		}

		@Actor
		public void method2(II_Result r) {
			resultCalculation(this.object, i -> r.r1 = i);
		}

		@Actor
		public void method3(II_Result r) {
			resultCalculation(this.object, i -> r.r2 = i);
		}

		private static void resultCalculation(InnerClass o, Consumer<Integer> consumer) {
			if (o == null) {
				consumer.accept(0);
			} else {
				consumer.accept(o.getSum());
			}
		}

		private class InnerClass {
			public final int var1;
			public final int var2;
			public final int var3;
			public final int var4;
			public final int var5;
			public final int var6;
			public final int var7;
			public final int var8;

			private InnerClass(int value) {
				leaking(this);
				var1 = value;
				var2 = value;
				var3 = value;
				var4 = value;
				var5 = value;
				var6 = value;
				var7 = value;
				var8 = value;
			}

			private int getSum() {
				return var1 + var2 + var3 + var4 + var5 + var6 + var7 + var8;
			}
		}
	}

	@JCStressTest
	@Outcome.Outcomes({
			@Outcome(id = "0, 0", expect = Expect.ACCEPTABLE, desc = "Object wasn't initialized for both Actors."),
			@Outcome(id = "0, 8", expect = Expect.ACCEPTABLE, desc = "Object wasn't initialized for first Actor but was initialized for second Actor."),
			@Outcome(id = "8, 0", expect = Expect.ACCEPTABLE, desc = "Object was initialized for first Actor but wasn't initialized for second Actor."),
			@Outcome(id = "8, 8", expect = Expect.ACCEPTABLE, desc = "Object wasn't initialized for both Actors."),
			@Outcome(expect = Expect.FORBIDDEN, desc = "Seeing partially constructed object.")
	})
	@State
	public static class FrozenFinalProperties {
		private InnerClass object;

		@Actor
		public void method1() {
			object = new InnerClass(1);
		}

		@Actor
		public void method2(II_Result r) {
			resultCalculation(this.object, i -> r.r1 = i);
		}

		@Actor
		public void method3(II_Result r) {
			resultCalculation(this.object, i -> r.r2 = i);
		}

		private static void resultCalculation(InnerClass o, Consumer<Integer> consumer) {
			if (o == null) {
				consumer.accept(0);
			} else {
				consumer.accept(o.getSum());
			}
		}

		private static class InnerClass {
			public final int var1;
			public final int var2;
			public final int var3;
			public final int var4;
			public final int var5;
			public final int var6;
			public final int var7;
			public final int var8;

			private InnerClass(int value) {
				var1 = value;
				var2 = value;
				var3 = value;
				var4 = value;
				var5 = value;
				var6 = value;
				var7 = value;
				var8 = value;
			}

			private int getSum() {
				return var1 + var2 + var3 + var4 + var5 + var6 + var7 + var8;
			}
		}
	}
}