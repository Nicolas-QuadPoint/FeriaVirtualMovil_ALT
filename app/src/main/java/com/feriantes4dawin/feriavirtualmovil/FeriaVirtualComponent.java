package com.feriantes4dawin.feriavirtualmovil;

import com.feriantes4dawin.feriavirtualmovil.data.datasources.local.FeriaVirtualDBProvider;
import com.feriantes4dawin.feriavirtualmovil.data.datasources.remote.FeriaVirtualAPIProvider;
import com.feriantes4dawin.feriavirtualmovil.data.repos.ProductoRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.ProductoRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.data.repos.SubastaRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.SubastaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.ui.auction.AuctionSaleActivity;
import com.feriantes4dawin.feriavirtualmovil.ui.auction.PushProductorActivity;
import com.feriantes4dawin.feriavirtualmovil.ui.auction.PushTransportistaActivity;
import com.feriantes4dawin.feriavirtualmovil.ui.home.HomeFragment;
import com.feriantes4dawin.feriavirtualmovil.ui.login.LoginActivity;
import com.feriantes4dawin.feriavirtualmovil.ui.main.MainActivity;
import com.feriantes4dawin.feriavirtualmovil.ui.proccesses.MyProcessesFragment;
import com.feriantes4dawin.feriavirtualmovil.ui.proccesses.MyProcessesViewModel;
import com.feriantes4dawin.feriavirtualmovil.ui.profile.MyProfileFragment;
import com.feriantes4dawin.feriavirtualmovil.ui.sales.CurrentSalesFragment;
import com.feriantes4dawin.feriavirtualmovil.ui.sales.SaleDetailActivity;
import com.feriantes4dawin.feriavirtualmovil.ui.util.JsonConverterProvider;

import javax.inject.Singleton;
import dagger.Component;

/**
 * FeriaVirtualComponent
 * 
 * Concentra todas las dependencias a injectar del proyecto,
 * enumerándolas en la cláusula Component. Para maś información
 * sobre dichas clases, explora cada una de estas.
 * 
 * También consulta información sobre Dagger y su método para 
 * permitir inyección de dependencias.
 * 
 * Cuando el proyecto es compilado, Dagger genera clases y métodos
 * basados en los mencionados aquí para permitir las características 
 * antes mencionadas. 
 * 
 */
@Singleton
@Component(
    modules={
        FeriaVirtualApplicationModule.class,
        JsonConverterProvider.class,
        FeriaVirtualAPIProvider.class,
        FeriaVirtualDBProvider.class,
        UsuarioRepositoryImpl.class,
        VentaRepositoryImpl.class,
        SubastaRepositoryImpl.class,
        ProductoRepositoryImpl.class
})
public interface FeriaVirtualComponent {

    /**
     * Se llama en la clase MyProfileFragment para inyectar 
     * sus dependencias. 
     * Se usa tal que así: 
     * <pre>{@code 
     * 
     * ... 
     * injectIntoMyProfileFragment(this); 
     * ... 
     * 
     * }</pre> 
     * Con eso, las dependencias marcadas con la anotación 
     * Inject serań inicializadas y estarán listas para usarse. 
     * 
     * Llama este método desde uno de los métodos callback del 
     * fragmento, como onViewCreated o onAttach. 
     * 
     * @see {@link javax.inject.Inject} 
     * @param myProfileFragment Una instancia de MyProfileFragment 
     */
    void injectIntoMyProfileFragment(MyProfileFragment myProfileFragment);


    /**
     * Se llama en la clase CurrentSalesFragment para inyectar 
     * sus dependencias. 
     * Se usa tal que así: 
     * <pre>{@code 
     * 
     * ... 
     * injectIntoCurrentSalesFragment(this); 
     * ... 
     * 
     * }</pre> 
     * Con eso, las dependencias marcadas con la anotación 
     * Inject serań inicializadas y estarán listas para usarse. 
     * 
     * Llama este método desde uno de los métodos callback del 
     * fragmento, como onViewCreated o onAttach. 
     * 
     * @see {@link javax.inject.Inject} 
     * @param currentSalesFragment Una instancia de CurrentSalesFragment 
     */
    void injectIntoCurrentSalesFragment(CurrentSalesFragment currentSalesFragment);

    
    /**
     * Se llama en la clase MyProcessesFragment para inyectar 
     * sus dependencias. 
     * Se usa tal que así: 
     * <pre>{@code 
     * 
     * ... 
     * injectIntoMyProcessesFragment(this); 
     * ... 
     * 
     * }</pre> 
     * Con eso, las dependencias marcadas con la anotación 
     * Inject serań inicializadas y estarán listas para usarse. 
     * 
     * Llama este método desde uno de los métodos callback del 
     * fragmento, como onViewCreated o onAttach. 
     * 
     * @see {@link javax.inject.Inject} 
     * @param myProcessesFragment Una instancia de MyProcessesFragment 
     */
    void injectIntoMyProcessesFragment(MyProcessesFragment myProcessesFragment);

    /**
     * Se llama en la clase SaleDetailActivity para inyectar 
     * sus dependencias. 
     * Se usa tal que así: 
     * <pre>{@code 
     * 
     * ... 
     * injectIntoSaleDetailActivity(this); 
     * ... 
     * 
     * }</pre> 
     * Con eso, las dependencias marcadas con la anotación 
     * Inject serań inicializadas y estarán listas para usarse. 
     * 
     * Llama este método desde uno de los métodos callback de 
     * la actividad, como en onCreate. 
     * 
     * @see {@link javax.inject.Inject} 
     * @param saleDetailActivity Una instancia de SaleDetailActivity
     */
    void injectIntoSaleDetailActivity(SaleDetailActivity saleDetailActivity);

    /**
     * Se llama en la clase LoginActivity para inyectar 
     * sus dependencias. 
     * Se usa tal que así: 
     * <pre>{@code 
     * 
     * ... 
     * injectIntoLoginActivity(this); 
     * ... 
     * 
     * }</pre> 
     * Con eso, las dependencias marcadas con la anotación 
     * Inject serań inicializadas y estarán listas para usarse. 
     * 
     * Llama este método desde uno de los métodos callback de 
     * la actividad, como en onCreate. 
     * 
     * @see {@link javax.inject.Inject} 
     * @param loginActivity Una instancia de LoginActivity 
     */
    void injectIntoLoginActivity(LoginActivity loginActivity);

    /**
     * Se llama en la clase MainActivity para inyectar 
     * sus dependencias. 
     * Se usa tal que así: 
     * <pre>{@code 
     * 
     * ... 
     * injectIntoMainActivity(this); 
     * ... 
     * 
     * }</pre> 
     * Con eso, las dependencias marcadas con la anotación 
     * Inject serań inicializadas y estarán listas para usarse. 
     * 
     * Llama este método desde uno de los métodos callback de 
     * la actividad, como en onCreate. 
     * 
     * @see {@link javax.inject.Inject} 
     * @param mainActivity Una instancia de MainActivity 
     */
    void injectIntoMainActivity(MainActivity mainActivity);

    /**
     * Se llama en la clase AuctionSaleActivity para inyectar 
     * sus dependencias. 
     * Se usa tal que así: 
     * <pre>{@code 
     * 
     * ... 
     * injectIntoAuctionSaleActivity(this); 
     * ... 
     * 
     * }</pre> 
     * Con eso, las dependencias marcadas con la anotación 
     * Inject serań inicializadas y estarán listas para usarse. 
     * 
     * Llama este método desde uno de los métodos callback de 
     * la actividad, como en onCreate. 
     * 
     * @see {@link javax.inject.Inject} 
     * @param auctionSaleActivity Una instancia de AuctionSaleActivity 
     */
    void injectIntoAuctionSaleActivity(AuctionSaleActivity auctionSaleActivity);

    /**
     * Se llama en la clase PushProductorActivity para inyectar
     * sus dependencias.
     * Se usa tal que así:
     * <pre>{@code
     *
     * ...
     * injectIntoAuctionSaleActivity(this);
     * ...
     *
     * }</pre>
     * Con eso, las dependencias marcadas con la anotación
     * Inject serań inicializadas y estarán listas para usarse.
     *
     * Llama este método desde uno de los métodos callback de
     * la actividad, como en onCreate.
     *
     * @see {@link javax.inject.Inject}
     * @param pushProductorActivity Una instancia de AuctionSaleActivity
     */
    void injectIntoPushProductorActivity(PushProductorActivity pushProductorActivity);

    /**
     * Se llama en la clase PushTransportistaActivity para inyectar
     * sus dependencias.
     * Se usa tal que así:
     * <pre>{@code
     *
     * ...
     * injectIntoAuctionSaleActivity(this);
     * ...
     *
     * }</pre>
     * Con eso, las dependencias marcadas con la anotación
     * Inject serań inicializadas y estarán listas para usarse.
     *
     * Llama este método desde uno de los métodos callback de
     * la actividad, como en onCreate.
     *
     * @see {@link javax.inject.Inject}
     * @param pushTransportistaActivity Una instancia de AuctionSaleActivity
     */
    void injectIntoPushTransportistaActivity(PushTransportistaActivity pushTransportistaActivity);

    /**
     * Se llama en la clase HomeFragment para inyectar 
     * sus dependencias. 
     * Se usa tal que así: 
     * <pre>{@code 
     * 
     * ... 
     * injectIntoHomeFragment(this); 
     * ... 
     * 
     * }</pre> 
     * Con eso, las dependencias marcadas con la anotación 
     * Inject serań inicializadas y estarán listas para usarse. 
     * 
     * Llama este método desde uno de los métodos callback del 
     * fragmento, como onViewCreated o onAttach. 
     * 
     * @see {@link javax.inject.Inject} 
     * @param homeFragment Una instancia de HomeFragment 
     */
    void injectIntoHomeFragment(HomeFragment homeFragment);

}
