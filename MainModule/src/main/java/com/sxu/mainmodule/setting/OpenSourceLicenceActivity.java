package com.sxu.mainmodule.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.sxu.basecomponent.activity.BaseActivity;
import com.sxu.basecomponent.adapter.BaseCommonAdapter;
import com.sxu.basecomponent.adapter.ViewHolder;
import com.sxu.basecomponent.manager.PathManager;
import com.sxu.basecomponent.manager.ThreadPoolManager;
import com.sxu.baselibrary.commonutils.CollectionUtil;
import com.sxu.baselibrary.commonutils.FileUtil;
import com.sxu.baselibrary.datasource.http.bean.BaseBean;
import com.sxu.mainmodule.R;

import java.io.FileInputStream;
import java.util.List;

public class OpenSourceLicenceActivity extends BaseActivity {

	private ListView licenceList;

	private List<LicenceItemBean> licenceListData;

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_open_source_licence_layout;
	}

	@Override
	protected void getViews() {
		licenceList = (ListView) findViewById(R.id.licence_list);
	}

	@Override
	protected void initActivity() {
		toolbar.setTitle("开源许可");
		ThreadPoolManager.executeTask(new Runnable() {
			@Override
			public void run() {
				readLicenceData();
			}
		});
	}

	private void readLicenceData() {
		String licenceJson = FileUtil.readAssetFile(this, "licences.json");
		if (TextUtils.isEmpty(licenceJson)) {
			return;
		}

		LicenceBean licencesInfo = BaseBean.fromJson(licenceJson, LicenceBean.class);
		if (licencesInfo != null) {
			licenceListData = licencesInfo.data;
		}
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				setLicenceAdapter();
			}
		});
	}

	private void setLicenceAdapter() {
		if (CollectionUtil.isEmpty(licenceListData)) {
			return;
		}

		licenceList.setAdapter(new BaseCommonAdapter<LicenceItemBean>(context, licenceListData, R.layout.item_licence_layout) {
			@Override
			public void convert(ViewHolder holder, LicenceItemBean paramT, int position) {
				holder.setText(R.id.name_text, paramT.name);
				holder.setText(R.id.licence_text, paramT.licence);
			}
		});
	}

	public class LicenceBean extends BaseBean {
		public List<LicenceItemBean> data;
	}

	public class LicenceItemBean extends BaseBean {
		/**
		 * 项目名称
		 */
		public String name;
		/**
		 * 项目许可内容
		 */
		public String licence;
	}
}
