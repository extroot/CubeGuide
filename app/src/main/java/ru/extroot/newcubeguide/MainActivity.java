package ru.extroot.newcubeguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

public class MainActivity extends AppCompatActivity
{
    /*
   TODO: Rewrite full app
   3x3x3:
       F2l     - 1
       OLL     - 2
       PLL     - 3
       TODO: Expert F2L, OLL, PLL
   3x3x3 EASY  - 22
   TODO: easy Fridrich, 4x4x4, 5x5x5, 2x2x2
   3x3x3 OH:
       OH_OLL  - 7
       OH_PLL  - 8
       OH_COLL - 14
   3x3x3 PRO:
       VHF2L   - 4
       OPF2L   - 5
       COLL    - 6
       OLE     - 10
       OLC     - 11
       WV      - 12
       SV      - 13
       VLS     - 18
       MW      - 19
       CLS     - 28
       ELL     - 29
       OELLCP  - 30
       ELS     - 31
       PLL_SC  - 32
       ZBLL:
           ZBLL_T        - 41
           ZBLL_U        - 42
           ZBLL_L        - 43
           ZBLL_H        - 44
           ZBLL_PI       - 45
           ZBLL_SUNE     - 46
           ZBLL_ANTISUNE - 47
   3x3x3 UZ    - 40 TODO: UZ titles (En & Ru)
   TODO: UZ 2x2x2, 4x4x4, 5x5x5
   2x2x2:
       CLL     - 9
       Ortega  - 23
       EG1     - 15
       EG2     - 16
       LEG1    - 17
       TODO: TCLL-
       TCLL+   - 24
   4x4x4 POLL  - 25
   5x5x5:
       L2C     - 26
       L2E     - 27
   Megaminx:
       MG_OLL  - 20
       MG_PLL  - 21
   Pyraminx:
       L3C     - 33
       L3E     - 34
   Square-1:
       EO      - 35
       CP      - 36
       EP      - 37
    */

    private static final int F2L_ID     = 1;
    private static final int PLL_ID     = 2;
    private static final int OLL_ID     = 3;
    private static final int VHF2L_ID   = 4;
    private static final int OPF2L_ID   = 5;
    private static final int COLL_ID    = 6;
    private static final int OH_OLL_ID  = 7;
    private static final int OH_PLL_ID  = 8;
    private static final int CLL_ID     = 9;
    private static final int OLE_ID     = 10;
    private static final int OLC_ID     = 11;
    private static final int WV_ID      = 12;
    private static final int SV_ID      = 13;
    private static final int OH_COLL_ID = 14;
    private static final int EG1_ID     = 15;
    private static final int EG2_ID     = 16;
    private static final int LEG1_ID    = 17;
    private static final int VLS_ID     = 18;
    private static final int MW_ID      = 19;
    private static final int MG_OLL_ID  = 20;
    private static final int MG_PLL_ID  = 21;
    private static final int EASY_3_ID  = 22;
    private static final int ORTEGA_ID  = 23;
    private static final int TCLLP_ID   = 24;
    private static final int POLL_ID    = 25;
    private static final int L2C_ID     = 26;
    private static final int L2E_ID     = 27;
    private static final int CLS_ID     = 28;
    private static final int ELL_ID     = 29;
    private static final int OELLCP_ID  = 30;
    private static final int ELS_ID     = 31;
    private static final int PLL_SC_ID  = 32;
    private static final int L3C_ID     = 33;
    private static final int L3E_ID     = 34;
    private static final int EO_ID      = 35;
    private static final int CP_ID      = 36;
    private static final int EP_ID      = 37;
    private static final int UZ_ID      = 40;
    private static final int ZBLL_T_ID        = 41;
    private static final int ZBLL_U_ID        = 42;
    private static final int ZBLL_L_ID        = 43;
    private static final int ZBLL_H_ID        = 44;
    private static final int ZBLL_PI_ID       = 45;
    private static final int ZBLL_SUNE_ID     = 46;
    private static final int ZBLL_ANTISUNE_ID = 47;

    private Toolbar toolbar;
    private String picMode, mode;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        DrawerBuilder drawerBuilder = new DrawerBuilder()
                .withActivity( this )
                .withDelayOnDrawerClose( -1 )
                .withToolbar( toolbar )
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName( R.string.easy3_header )
                                .withIdentifier( EASY_3_ID ),
                        new ExpandableDrawerItem()
                                .withName( R.string.header_3x3x3 )
                                .withSelectable( false )
                                .withSubItems(
                                        new SecondaryDrawerItem()
                                                .withName( R.string.f2l_header )
                                                .withLevel( 2 )
                                                .withIdentifier( F2L_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.oll_header )
                                                .withLevel( 2 )
                                                .withIdentifier( OLL_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.pll_header )
                                                .withLevel( 2 )
                                                .withIdentifier( PLL_ID )
                                ),
                        new ExpandableDrawerItem()
                                .withName( R.string.header_3x3x3_oh )
                                .withSelectable( false )
                                .withSubItems(
                                        new SecondaryDrawerItem()
                                                .withName( R.string.oh_oll_header )
                                                .withLevel( 2 )
                                                .withIdentifier( OH_OLL_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.oh_pll_header )
                                                .withLevel( 2 )
                                                .withIdentifier( OH_PLL_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.oh_coll_header )
                                                .withLevel( 2 )
                                                .withIdentifier( OH_COLL_ID )
                                ),
                        new ExpandableDrawerItem()
                                .withName( R.string.header_3x3x3_pro )
                                .withSelectable( false )
                                .withSubItems(
                                        new SecondaryDrawerItem()
                                                .withName( R.string.vh_header )
                                                .withLevel( 2 )
                                                .withIdentifier( VHF2L_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.op_header)
                                                .withLevel( 2 )
                                                .withIdentifier( OPF2L_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.coll_header )
                                                .withLevel( 2 )
                                                .withIdentifier( COLL_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.ole_header )
                                                .withLevel( 2 )
                                                .withIdentifier( OLE_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.olc_header )
                                                .withLevel( 2 )
                                                .withIdentifier( OLC_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.wv_header )
                                                .withLevel( 2 )
                                                .withIdentifier( WV_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.sv_header )
                                                .withLevel( 2 )
                                                .withIdentifier( SV_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.vls_header )
                                                .withLevel( 2 )
                                                .withIdentifier( VLS_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.mw_header )
                                                .withLevel( 2 )
                                                .withIdentifier( MW_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.cls_header )
                                                .withLevel( 2 )
                                                .withIdentifier( CLS_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.ell_header )
                                                .withLevel( 2 )
                                                .withIdentifier( ELL_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.oellcp_header )
                                                .withLevel( 2 )
                                                .withIdentifier( OELLCP_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.els_header )
                                                .withLevel( 2 )
                                                .withIdentifier( ELS_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.pll_sc_header )
                                                .withLevel( 2 )
                                                .withIdentifier( PLL_SC_ID ),
                                        new ExpandableDrawerItem()
                                                .withName( R.string.zbll_header )
                                                .withSelectable( false )
                                                .withLevel( 2 )
                                                .withSubItems(
                                                        new SecondaryDrawerItem()
                                                                .withName( R.string.zbll_t_header )
                                                                .withLevel( 3 )
                                                                .withIdentifier( ZBLL_T_ID ),
                                                        new SecondaryDrawerItem()
                                                                .withName( R.string.zbll_u_header)
                                                                .withLevel( 3 )
                                                                .withIdentifier( ZBLL_U_ID ),
                                                        new SecondaryDrawerItem()
                                                                .withName( R.string.zbll_l_header )
                                                                .withLevel( 3 )
                                                                .withIdentifier( ZBLL_L_ID ),
                                                        new SecondaryDrawerItem()
                                                                .withName( R.string.zbll_h_header )
                                                                .withLevel( 3 )
                                                                .withIdentifier( ZBLL_H_ID ),
                                                        new SecondaryDrawerItem()
                                                                .withName( R.string.zbll_pi_header )
                                                                .withLevel( 3 )
                                                                .withIdentifier( ZBLL_PI_ID ),
                                                        new SecondaryDrawerItem()
                                                                .withName( R.string.zbll_sune_header )
                                                                .withLevel( 3 )
                                                                .withIdentifier( ZBLL_SUNE_ID ),
                                                        new SecondaryDrawerItem()
                                                                .withName( R.string.zbll_antisune_header )
                                                                .withLevel( 3 )
                                                                .withIdentifier( ZBLL_ANTISUNE_ID )
                                                )
                                ),
                        new PrimaryDrawerItem()
                                .withName( R.string.uz_header )
                                .withIdentifier( UZ_ID ),
                        new ExpandableDrawerItem()
                                .withName( R.string.header_2x2x2 )
                                .withSelectable( false )
                                .withSubItems(
                                        new SecondaryDrawerItem()
                                                .withName( R.string.ortega_header )
                                                .withLevel( 2 )
                                                .withIdentifier( ORTEGA_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.cll_header )
                                                .withLevel( 2 )
                                                .withIdentifier( CLL_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.eg1__header )
                                                .withLevel( 2 )
                                                .withIdentifier( EG1_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.eg2__header )
                                                .withLevel( 2 )
                                                .withIdentifier( EG2_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.leg1__header )
                                                .withLevel( 2 )
                                                .withIdentifier( LEG1_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.tcllp_header )
                                                .withLevel( 2 )
                                                .withIdentifier( TCLLP_ID )
                                ),
                        new PrimaryDrawerItem()
                                .withName( R.string.poll_header)
                                .withIdentifier( POLL_ID ),
                        new ExpandableDrawerItem()
                                .withName( R.string.header_5x5x5 )
                                .withSelectable( false )
                                .withSubItems(
                                        new SecondaryDrawerItem()
                                                .withName( R.string.l2c_header )
                                                .withLevel( 2 )
                                                .withIdentifier( L2C_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.l2e_header )
                                                .withLevel( 2 )
                                                .withIdentifier( L2E_ID )
                                ),
                        new ExpandableDrawerItem()
                                .withName( R.string.header_mg )
                                .withSelectable( false )
                                .withSubItems(
                                        new SecondaryDrawerItem()
                                                .withName( R.string.mg_oll_header )
                                                .withLevel( 2 )
                                                .withIdentifier( MG_OLL_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.mg_pll_header )
                                                .withLevel( 2 )
                                                .withIdentifier( MG_PLL_ID )
                                ),
                        new ExpandableDrawerItem()
                                .withName( R.string.header_pyraminx )
                                .withSelectable( false )
                                .withSubItems(
                                        new SecondaryDrawerItem()
                                                .withName( R.string.l3c_header )
                                                .withLevel( 2 )
                                                .withIdentifier( L3C_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.l3e_header )
                                                .withLevel( 2 )
                                                .withIdentifier( L3E_ID )
                                ),
                        new ExpandableDrawerItem()
                                .withName( R.string.header_square1 )
                                .withSelectable( false )
                                .withSubItems(
                                        new SecondaryDrawerItem()
                                                .withName( R.string.eo_header )
                                                .withLevel( 2 )
                                                .withIdentifier( EO_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.cp_header )
                                                .withLevel( 2 )
                                                .withIdentifier( CP_ID ),
                                        new SecondaryDrawerItem()
                                                .withName( R.string.ep_header )
                                                .withLevel( 2 )
                                                .withIdentifier( EP_ID )
                                )
                )
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    switch ( (int) drawerItem.getIdentifier() ) {
                        case EASY_3_ID:  tutorials_3x3x3(); return false;

                        case F2L_ID:     mode = picMode = "f2l"; break;
                        case OLL_ID:     mode = picMode = "oll"; break;
                        case PLL_ID:     mode = picMode = "pll"; break;

                        case OH_OLL_ID:  mode = "oh_oll";  picMode = "oll";  break;
                        case OH_PLL_ID:  mode = "oh_pll";  picMode = "pll";  break;
                        case OH_COLL_ID: mode = "oh_coll"; picMode = "coll"; break;

                        case COLL_ID:    mode = picMode = "coll";   break;
                        case OPF2L_ID:   mode = picMode = "op";     break;
                        case VHF2L_ID:   mode = picMode = "vh";     break;
                        case WV_ID:      mode = picMode = "wv";     break;
                        case SV_ID:      mode = picMode = "sv";     break;
                        case MW_ID:      mode = picMode = "mw";     break;
                        case OLE_ID:     mode = picMode = "ole";    break;
                        case OLC_ID:     mode = picMode = "olc";    break;
                        case VLS_ID:     mode = picMode = "vls";    break;
                        case CLS_ID:     mode = picMode = "cls";    break;
                        case ELL_ID:     mode = picMode = "ell";    break;
                        case OELLCP_ID:  mode = picMode = "oellcp"; break;
                        case ELS_ID:     mode = picMode = "els";    break;
                        case PLL_SC_ID:  mode = picMode = "pll_sc"; break;

                        case ZBLL_T_ID:        mode = picMode = "zbll_t";        break;
                        case ZBLL_U_ID:        mode = picMode = "zbll_u";        break;
                        case ZBLL_L_ID:        mode = picMode = "zbll_l";        break;
                        case ZBLL_H_ID:        mode = picMode = "zbll_h";        break;
                        case ZBLL_PI_ID:       mode = picMode = "zbll_pi";       break;
                        case ZBLL_SUNE_ID:     mode = picMode = "zbll_sune";     break;
                        case ZBLL_ANTISUNE_ID: mode = picMode = "zbll_antisune"; break;

                        case UZ_ID:      mode = picMode = "uz"; break;

                        case CLL_ID:     mode = picMode = "cll";          break;
                        case ORTEGA_ID:  mode = picMode = "ortega";       break;
                        case EG1_ID:     mode = "eg1_";  picMode = "cll"; break;
                        case EG2_ID:     mode = "eg2_";  picMode = "cll"; break;
                        case LEG1_ID:    mode = "leg1_"; picMode = "cll"; break;
                        case TCLLP_ID:   mode = picMode = "tcllp";        break;

                        case POLL_ID:    mode = picMode = "poll"; break;

                        case L2C_ID:     mode = picMode = "l2c"; break;
                        case L2E_ID:     mode = picMode = "l2e"; break;

                        case MG_OLL_ID:  mode = picMode = "mg_oll"; break;
                        case MG_PLL_ID:  mode = picMode = "mg_pll"; break;

                        case L3C_ID:     mode = picMode = "l3c"; break;
                        case L3E_ID:     mode = picMode = "l3e"; break;

                        case EO_ID:      mode = picMode = "eo"; break;
                        case CP_ID:      mode = picMode = "cp"; break;
                        case EP_ID:      mode = picMode = "ep"; break;

                        default: return true;
                    }
                    toolbar.setTitle( getResources().getString( getResources().getIdentifier( mode + "_header", "string", getPackageName() ) ) );
                    Draw();
                    return false;
                })
                .withSavedInstance( savedInstanceState );
        Drawer mDrawer = drawerBuilder.build();
        // TODO: save method, when app stops.
        mDrawer.setSelection( EASY_3_ID );
    }

    // TODO: normal inflate easy3x3x3 and others in Draw() function.
    void tutorials_3x3x3()
    {
        toolbar.setTitle( R.string.easy3_header );

        LinearLayout mainLayout = findViewById( R.id.main_view );
        mainLayout.removeAllViews();

        LayoutInflater inflater = (LayoutInflater) this.getSystemService( LAYOUT_INFLATER_SERVICE );
        View childLayout = inflater.inflate( R.layout.easy3, findViewById( R.id.easy3 ));
        mainLayout.addView( childLayout );
    }

    void Draw()
    {
        // TODO: use Dimens.
        int picLen = 250;
        int textSize = 16;
        LinearLayout mainLayout = findViewById( R.id.main_view );
        mainLayout.removeAllViews();
        mainLayout.setPadding( 0, 5, 0, 40 );

        ScrollView scrollview = findViewById( R.id.main_scroll );
        scrollview.scrollTo( 0,0 );
        for ( int i = 0; i < Integer.parseInt( getResources().getString( getResources().getIdentifier( mode + "_count", "string", getPackageName() ) ) ); i++ )
        {
            ImageView image = new ImageView( this );
            image.setImageResource( getResources().getIdentifier( picMode + i, "drawable", getPackageName() ) );
            image.setLayoutParams( new LinearLayout.LayoutParams( picLen, picLen ) );

            LinearLayout line = new LinearLayout( new ContextThemeWrapper( this, R.style.line ) );

            TextView algText = new TextView( this );

            String alg = getResources().getString( getResources().getIdentifier( mode + i, "string", getPackageName() ) );

            if ( alg.equals( "" ) ) continue;
            algText.setText( alg );
            algText.setGravity( Gravity.CENTER_VERTICAL );
            algText.setTextSize( textSize );
            algText.setPadding( 15, 0, 10, 0 );

            // TODO: make orientation string resources in methods.
            if ( picMode.equals( "l3c" ) || picMode.equals( "eo" ) || picMode.equals( "cp" ) || picMode.equals( "ep" ) )
            {
                image.setLayoutParams( new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT) );
                line.setOrientation( LinearLayout.VERTICAL );
                line.setGravity( Gravity.CENTER_HORIZONTAL );
                algText.setGravity( Gravity.CENTER_HORIZONTAL );
            }
            String title = getResources().getString( getResources().getIdentifier( picMode + i + "_title", "string", getPackageName() ) );
            if ( !title.equals( "" ) )
            {
                TextView titleView = new TextView( this );
                titleView.setText( title );
                // TODO: use styles for text, separators and titles.
                titleView.setPadding( 0, 20, 0, 0 );
                titleView.setTextSize( textSize + 4 );
                titleView.setTextAlignment( TextView.TEXT_ALIGNMENT_CENTER );

                mainLayout.addView( titleView );
            } else
            {
                View sep = new View( this );
                LinearLayout.LayoutParams sepParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, 1 );
                sep.setLayoutParams( sepParams );
                // TODO: Dark separator color.
                sep.setBackgroundColor( getColor( R.color.material_drawer_divider ) );
                sep.setPadding( 3, 1, 1, 3 );
                mainLayout.addView( sep );
            }
            line.addView( image );
            line.addView( algText );
            mainLayout.addView( line );
        }
    }
}