package com.cetcme.rcldandroidZhejiang.xia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.cetcme.rcldandroidZhejiang.R;

public class SearchRecordListActivity extends Activity implements
		OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.record_search_layout);
	}

	@SuppressWarnings("null")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.pay_insurance_ll:

			intent.setClass(SearchRecordListActivity.this,
					SearcherPayEnsuranceActivity.class);
			startActivity(intent);
			break;

		case R.id.claim_ll:
			intent.setClass(SearchRecordListActivity.this,
					SearcherClaimActivity.class);
			startActivity(intent);
			break;

		case R.id.transaction_ll:
			intent.setClass(SearchRecordListActivity.this,
					SearcherTradeActivity.class);
			startActivity(intent);
			break;

		case R.id.ship_detect_ll:
			intent.setClass(SearchRecordListActivity.this,
					SearcherShipDetectActivity.class);
			startActivity(intent);
			break;

		case R.id.drawing_check_ll:
			intent.setClass(SearchRecordListActivity.this,
					SearcherDrawingCheckActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
