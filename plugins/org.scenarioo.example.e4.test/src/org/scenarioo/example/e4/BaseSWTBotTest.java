/* Copyright (c) 2014, scenarioo.org Development Team
 * All rights reserved.
 *
 * See https://github.com/scenarioo?tab=members
 * for a complete list of contributors to this project.
 *
 * Redistribution and use of the Scenarioo Examples in source and binary forms,
 * with or without modification, are permitted provided that the following
 * conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice, this
 *   list of conditions and the following disclaimer in the documentation and/or
 *   other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.scenarioo.example.e4;

import java.util.Date;

import org.eclipse.swtbot.e4.finder.widgets.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.scenarioo.example.e4.rules.OrderOverviewCleanUpRule;
import org.scenarioo.example.e4.rules.ScenariooRule;

public class BaseSWTBotTest {

	public static final String PART_ID_ORDER_OVERVIEW = "org.scenarioo.example.e4.orders.part.ordersoverview";
	private static final Date scenariooBuildDate = new Date();
	private static final UnitTestWatcher buildFailedWatcher = new UnitTestWatcher();
	protected static SWTBot bot;
	protected static SWTWorkbenchBot wbBot;
	protected final String useCaseName = "Orders";
	protected final ScenariooWriterHelper scenariooWriterHelper = new ScenariooWriterHelper(scenariooBuildDate);

	@Rule
	public final TestWatcher testWatchterRule = new TestWatcher() {
		@Override
		protected void failed(final Throwable e, final Description description) {
			if (buildFailedWatcher.success) {
				buildFailedWatcher.success = false;
				scenariooWriterHelper.writeFailedBuildFile();
			}
			scenariooWriterHelper.writeScenariooFailedFile();
		}

		@Override
		protected void finished(final Description description) {
			scenariooWriterHelper.flush();
		}
	};

	@Rule
	public final ScenariooRule scenariooRule;

	@Rule
	public final OrderOverviewCleanUpRule orderOverviewCleanUpRule = new OrderOverviewCleanUpRule();

	public BaseSWTBotTest() {
		scenariooWriterHelper.setUseCaseName("Orders");
		scenariooRule = new ScenariooRule(scenariooWriterHelper);
	}

	@BeforeClass
	public static void setup() throws Exception {
		// don't use SWTWorkbenchBot here which relies on Platform 3.x
		bot = new SWTBot();
		wbBot = new SWTWorkbenchBot(EclipseContextHelper.getEclipseContext());
	}

	private static class UnitTestWatcher {
		private boolean success = true;
	}
}
