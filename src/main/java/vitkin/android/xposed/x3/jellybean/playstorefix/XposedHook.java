/*
 * #%L
 * X3 Jelly Bean Play Store Fix
 * %%
 * Copyright (C) 2014 Victor Itkin
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package vitkin.android.xposed.x3.jellybean.playstorefix;

import android.app.Activity;
import android.view.MenuItem;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class XposedHook implements IXposedHookLoadPackage
{
  @Override
  public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable
  {
    if (!lpparam.packageName.equals("com.android.vending"))
    {
      return;
    }

    final XC_MethodHook mh = new XC_MethodHook()
    {
      @Override
      protected void beforeHookedMethod(MethodHookParam param) throws Throwable
      {
        final MenuItem item = (MenuItem) param.args[1];
        final String tc = item.getTitleCondensed().toString();
        item.setTitleCondensed(tc);
      }
    };

    findAndHookMethod(Activity.class, "onMenuItemSelected",
                      "int", MenuItem.class, mh);
  }
}
