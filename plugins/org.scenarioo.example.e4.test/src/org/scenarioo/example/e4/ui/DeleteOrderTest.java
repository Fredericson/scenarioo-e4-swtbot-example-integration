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

package org.scenarioo.example.e4.ui;

import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scenarioo.example.e4.ScenariooTestWrapper;
import org.scenarioo.example.e4.pages.SearchOrdersDialogPageObject;
import org.scenarioo.example.e4.rules.CreateTempOrderRule;
import org.scenarioo.example.e4.rules.InitOrderOverviewRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SWTBotJunit4ClassRunner.class)
public class DeleteOrderTest extends ScenariooTestWrapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteOrderTest.class);

	@Rule
	public InitOrderOverviewRule initOrderOverview = new InitOrderOverviewRule();

	@Rule
	public CreateTempOrderRule createTempOrderRule = new CreateTempOrderRule();

	@Test
	public void execute() {

		generateDocuForInitialView();

		SWTBotTree tree = bot.tree();
		SWTBotMenu menu = getContextMenuAndGenerateDocu(tree, CreateTempOrderRule.ORDER_NUMBER_TEMP, "Delete Order");
		LOGGER.info(menu.toString());

		clickMenuEntryAndGenerateDocu(menu);

		// Assert 5 Orders available in OrderOverview
		Assert.assertEquals(initOrderOverview.getInitializedOrdersInOrderOverview(), tree.rowCount());

		// Verify Order has been deleted
		verifyTempOrderHasBeenDeleted();

		LOGGER.info(getClass().getSimpleName() + " successful!");
	}

	private void verifyTempOrderHasBeenDeleted() {

		SearchOrdersDialogPageObject searchOrdersDialog = new SearchOrdersDialogPageObject(
				scenariooWriterHelper);

		searchOrdersDialog.open();

		searchOrdersDialog.enterOrderNumber(CreateTempOrderRule.ORDER_NUMBER_TEMP);

		searchOrdersDialog.startSearch();

		SWTBotTable table = bot.table();
		int rowCountAfterDelete = table.rowCount();

		Assert.assertEquals(0, rowCountAfterDelete);

		bot.button("Cancel").click();
	}
}
