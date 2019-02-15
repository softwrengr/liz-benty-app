package com.square.apple.pdf_app.fragments;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.square.apple.pdf_app.R;
import com.square.apple.pdf_app.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Intent.EXTRA_TEXT;


/**
 * A simple {@link Fragment} subclass.
 */
public class OurCoachesFragment extends Fragment implements View.OnClickListener {


    public static OurCoachesFragment createFor(String text) {
        OurCoachesFragment fragment = new OurCoachesFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    View parentView;

    @BindView(R.id.iv_liz_bentley)
    CircleImageView ivLizBentley;
    @BindView(R.id.iv_karmen_dadourian)
    CircleImageView ivKarmenDadourian;
    @BindView(R.id.iv_georgina_miranda)
    CircleImageView ivGeorgina;
    @BindView(R.id.iv_deanne_carter)
    CircleImageView ivDeanneCarter;
    @BindView(R.id.iv_alissa)
    CircleImageView ivAlissa;
    @BindView(R.id.iv_gina_esposito)
    CircleImageView ivGinaEsposito;
    @BindView(R.id.iv_augusta_reese)
    CircleImageView ivAugustaReese;
    @BindView(R.id.iv_laura)
    CircleImageView ivLaura;
    @BindView(R.id.iv_kerith_mcelroy)
    CircleImageView ivKerithMcElroy;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_our_coaches, container, false);

        ButterKnife.bind(this, parentView);
        ivLizBentley.setOnClickListener(this);
        ivKarmenDadourian.setOnClickListener(this);
        ivGeorgina.setOnClickListener(this);
        ivDeanneCarter.setOnClickListener(this);
        ivAlissa.setOnClickListener(this);
        ivGinaEsposito.setOnClickListener(this);
        ivAugustaReese.setOnClickListener(this);
        ivLaura.setOnClickListener(this);
        ivKerithMcElroy.setOnClickListener(this);

        customActionBar();


        return parentView;
    }

    @Override
    public void onClick(View view) {



        switch (view.getId()) {
            case R.id.iv_liz_bentley:
                gotoWebview("https://www.lizbentley.com/our-team/liz-bentley","Liz Bentley");
                break;
            case R.id.iv_karmen_dadourian:
                gotoWebview("https://www.lizbentley.com/our-team/karmen-dadourian","Karmen Dadourian");
                break;

            case R.id.iv_georgina_miranda:
                gotoWebview("https://www.lizbentley.com/our-team/georgina-miranda","Georgina Miranda");
                break;

            case R.id.iv_deanne_carter:
                gotoWebview("https://www.lizbentley.com/our-team/deanne-carter","Deanne Carter");
                break;

            case R.id.iv_alissa:
                gotoWebview("https://www.lizbentley.com/our-team/alissa-finerman","Alissa Finerman");
                break;

            case R.id.iv_gina_esposito:
                gotoWebview("https://www.lizbentley.com/our-team/gina-esposito","Gina Esposito");
                break;

            case R.id.iv_augusta_reese:
                gotoWebview("https://www.lizbentley.com/our-team/augusta-reese","Augusta Reese");
                break;

            case R.id.iv_laura:
                gotoWebview("https://www.lizbentley.com/our-team/laura-caban","Laura Caban");
                break;

            case R.id.iv_kerith_mcelroy:
                gotoWebview("https://www.lizbentley.com/our-team/kerith-mcelroy","Kerith McElroy");
                break;

        }
    }

    private void gotoWebview(String strUrl,String strTitle) {
        Utilities.putValueInEditor(getActivity()).putString("title",strTitle).commit();
        Utilities.putValueInEditor(getActivity()).putString("url", strUrl).commit();
        Utilities.connectFragment(getActivity(), new WebviewFragment());

        ///test

    }

    public void customActionBar() {
        android.support.v7.app.ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView tvTitle = mCustomView.findViewById(R.id.title);
        tvTitle.setText("Our Coaches");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.show();

    }
}
