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

import org.eclipse.swtbot.e4.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.BeforeClass;

public class OrderOverviewWithSomeOrders extends RemoveAllOrderFromOrderOverview {

	protected static int ordersCountWithOrderAsOrderNumber;
	protected static int initializedOrdersInOrderOverview;

	@BeforeClass
	public static void setupOrder() throws Exception {
		searchFourOrders();
		initializedOrdersInOrderOverview = bot.tree().rowCount();
	}

	private static void searchFourOrders() {

		SWTBotView view = wbBot.partById(PART_ID_ORDER_OVERVIEW);
		view.toolbarButton("Search Order").click();

		SWTBotText text = bot.textWithLabel("&Order Number");
		text.typeText("Order");

		bot.buttonWithTooltip("Start Search").click();

		// Select Item in Table
		SWTBotTable table = bot.table();
		OrderOverviewWithSomeOrders.ordersCountWithOrderAsOrderNumber = table.rowCount();
		table.click(0, 5);
		bot.sleep(1000);
		table.click(1, 5);
		bot.sleep(1000);
		table.click(3, 5);
		bot.sleep(1000);
		table.click(5, 5);
		bot.sleep(1000);

		// click Finish
		bot.button("OK").click();

	}
}
