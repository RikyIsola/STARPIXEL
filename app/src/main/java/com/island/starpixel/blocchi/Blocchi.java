package com.island.starpixel.blocchi;
import com.island.*;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.solidi.*;
import com.island.starpixel.blocchi.solidi.armi.*;
import com.island.starpixel.blocchi.solidi.liquidi.*;
import com.island.starpixel.blocchi.solidi.mobili.*;
import com.island.starpixel.blocchi.solidi.mobili.ai.*;
import com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti.*;
import com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti.combattivi.*;
import com.island.starpixel.blocchi.solidi.porte.*;
import com.island.starpixel.blocchi.solidi.scudi.*;
import com.island.starpixel.blocchi.solidi.torcia.*;
import com.island.starpixel.blocchi.strumenti.*;
import com.island.starpixel.blocchi.strumenti.attrezzi.*;
import com.island.starpixel.blocchi.strumenti.lanciati.*;
import com.island.starpixel.blocchi.strumenti.proiettili.*;
import com.island.starpixel.blocchi.strumenti.scudi.*;
import com.island.starpixel.blocchi.terreni.*;
import java.util.*;
public class Blocchi
{
	public Random random=new Random();
	private String normales="";
	private String quasispazios="QUASI";
	public int normale=0;
	public int quasispazio=1;
	public int dimensioni=2;
	public int uomini=0;
	public int magon=1;
	public int oscuri=2;
	public int zombie=3;
	public int golem=4;
	public String zero="0";
	public String inventariolan="j";
	public String selezionato="i";
	public String x="X";
	public String nome="NAME";
	public String seme="SEED";
	public String chunkx="CHUNKX";
	public String chunky="CHUNKY";
	public String modalita="MODE";
	public String vita="LIFE";
	public String dimensione="DIMENSION";
	public String fileZombie="ZOMBIE";
	public String fileMagon="MAGON";
	public String y="Y";
	public String spazio=" ";
	public String tempo="TIME";
	public String luce="LIGHT";
	public String creachunk="k";
	public String id="ID";
	public String creaentita="l";
	public String inventario="INVENTORY";
	public String fine="g";
	public String rottura="h";
	public String rimuovi="m";
	public String finetocco="n";
	public String crea="f";
	public String giu="o";
	public String su="p";
	public String iniziotocco="q";
	public String comandi="a";
	public String teletrasporta="b";
	public String eliminachunk="c";
	public String motori="d";
	public String armi="e";
	public String rotazione="r";
	public String banca="s";
	public String luceLan="t";
	public String creazione="u";
	public String ripristino="v";
	public String cambioStato="w";
	public String stato="STATE";
	public String statoVuoto="0";
	public String statoQualcosa="1";
	public String statoAlleati="2";
	public String statoNemici="3";
	public String statoVita="4";
	public String statoNebula="5";
	public String statoNero="6";
	public String statoZombie="7";
	public String statoBoss="8";
	public String statoGemma="9";
	public String statoQuasispazio="10";
	//public String[]listaComandi={inventariolan,selezionato,x,y,creachunk,creaentita,rottura,rimuovi,finetocco,crea,giu,su,iniziotocco,comandi,teletrasporta,eliminachunk,motori,armi,rotazione,banca,luceLan,creazione,ripristino,cambioStato};
	public Vuoto Vuoto=new Vuoto();
	public Erba Erba=new Erba();
	public Giocatore Giocatore=new Giocatore();
	public AltroGiocatore AltroGiocatore=new AltroGiocatore();
	public Albero Albero=new Albero();
	public Bufalo Bufalo=new Bufalo();
	public Sabbia Sabbia=new Sabbia();
	public Cactus Cactus=new Cactus();
	public Cammello Cammello=new Cammello();
	public Teletrasporto Teletrasporto=new Teletrasporto();
	public ConsoleDiComando ConsoleDiComando=new ConsoleDiComando();
	public Sensori Sensori=new Sensori();
	public Motore Motore=new Motore();
	public Arma1 Arma1=new Arma1();
	public Siluro1 Siluro1=new Siluro1();
	public Rotatore Rotatore=new Rotatore();
	public Neve Neve=new Neve();
	public Pinguino Pinguino=new Pinguino();
	public MontagnaInnevata MontagnaInnevata=new MontagnaInnevata();
	public TerrenoFerro TerrenoFerro=new TerrenoFerro();
	public AttrezziFerro StrumentiFerro=new AttrezziFerro();
	public Uomo Uomo=new Uomo();
	public PallaDiNeve PallaDiNeve=new PallaDiNeve();
	public Magon Magon=new Magon();
	public Laser1 Laser1=new Laser1();
	public MuroFerro MuroFerro=new MuroFerro();
	public Scudo1 Scudo1=new Scudo1();
	public Esplosione Esplosione=new Esplosione();
	public Scudi1 Scudi1=new Scudi1();
	public Pietra Pietra=new Pietra();
	public MuroPietra MuroPietra=new MuroPietra();
	public Montagna Montagna=new Montagna();
	public Daino Daino=new Daino();
	public Scudi2 Scudi2=new Scudi2();
	public Scudo2 Scudo2=new Scudo2();
	public Arma2 Arma2=new Arma2();
	public Siluro2 Siluro2=new Siluro2();
	public LuceTeletrasporto LuceTeletrasporto=new LuceTeletrasporto();
	public BancaDati BancaDati=new BancaDati();
	public TorciaLegno TorciaLegno=new TorciaLegno();
	public LuceElettrica LuceElettrica=new LuceElettrica();
	public Oscuro Oscuro=new Oscuro();
	public Laser2 Laser2=new Laser2();
	public Laser3 Laser3=new Laser3();
	public Laser4 Laser4=new Laser4();
	public OscuroReale OscuroReale=new OscuroReale();
	public PortaFerro PortaFerro=new PortaFerro();
	public Acqua Acqua=new Acqua();
	public AcquaPalude AcquaPalude=new AcquaPalude();
	public AcquaNera AcquaNera=new AcquaNera();
	public ReMagon ReMagon=new ReMagon();
	public Ghiaccio Ghiaccio=new Ghiaccio();
	public PortaPietra PortaPietra=new PortaPietra();
	public Diamante Diamante=new Diamante();
	public Zombie Zombie=new Zombie();
	public GeneratoreBuchiNeri GeneratoreBuchiNeri=new GeneratoreBuchiNeri();
	public Golem Golem=new Golem();
	public PortaVirus PortaVirus=new PortaVirus();
	public Virus Virus=new Virus();
	public Legno Legno=new Legno();
	public Palude Palude=new Palude();
	public Rana Rana=new Rana();
	public Cuore Cuore=new Cuore();
	public TerrenoLegno TerrenoLegno=new TerrenoLegno();
	public MuroLegno MuroLegno=new MuroLegno();
	public AttrezziLegno AttrezziLegno=new AttrezziLegno();
	public PortaLegno PortaLegno=new PortaLegno();
	public ArcoLegno ArcoLegno=new ArcoLegno();
	public Supertanium Supertanium=new Supertanium();
	public MuroNeve MuroNeve=new MuroNeve();
	public AttrezziCactus AttrezziCactus=new AttrezziCactus();
	public TerrenoCactus TerrenoCactus=new TerrenoCactus();
	public MuroCactus MuroCactus=new MuroCactus();
	public TorciaCactus TorciaCactus=new TorciaCactus();
	public PortaCactus PortaCactus=new PortaCactus();
	public ArcoCactus ArcoCactus=new ArcoCactus();
	public AttrezziPietra AttrezziPietra=new AttrezziPietra();
	public ArcoFerro ArcoFerro=new ArcoFerro();
	public MuroErba MuroErba=new MuroErba();
	public Ferro Ferro=new Ferro();
	public MuroPalude MuroPalude=new MuroPalude();
	public ArcoPietra ArcoPietra=new ArcoPietra();
	public AttrezziFerro AttrezziFerro=new AttrezziFerro();
	public MuroSabbia MuroSabbia=new MuroSabbia();
	public Blocco[]blocchi=
	{
		Vuoto,Erba,Giocatore,AltroGiocatore,Albero,Bufalo,Sabbia,Cactus,Cammello,Teletrasporto,
		Sensori,ConsoleDiComando,Motore,Arma1,Siluro1,Rotatore,Neve,Pinguino,MontagnaInnevata,TerrenoFerro,
		StrumentiFerro,Uomo,PallaDiNeve,Magon,Laser1,MuroFerro,Scudo1,Esplosione,Scudi1,Pietra,
		MuroPietra,Montagna,Daino,Scudi2,Scudo2,Arma2,Siluro2,LuceTeletrasporto,BancaDati,TorciaLegno,
		LuceElettrica,Oscuro,Laser2,Laser3,Laser4,OscuroReale,PortaFerro,Acqua,AcquaPalude,AcquaNera,
		ReMagon,Ghiaccio,PortaPietra,Diamante,GeneratoreBuchiNeri,Zombie,Golem,PortaVirus,Virus,Legno,
		Palude,Rana,Cuore,TerrenoLegno,MuroLegno,AttrezziLegno,PortaLegno,ArcoLegno,Supertanium,MuroNeve,
		AttrezziCactus,TerrenoCactus,MuroCactus,TorciaCactus,PortaCactus,ArcoCactus,AttrezziPietra,ArcoFerro,MuroErba,Ferro,
		MuroPalude,ArcoPietra,AttrezziFerro,MuroSabbia
	};
	public Blocco[]terreni={Erba,Sabbia,Neve,Pietra,Palude};
	public Blocco[]animali={Bufalo,Cammello,Pinguino,Daino,Rana};
	public Blocco[]piante={Albero,Cactus,MontagnaInnevata,Montagna,Albero};
	public Blocco[]acque={Acqua,Acqua,Ghiaccio,Acqua,AcquaPalude};
	public Blocco[]centriSpeciale={Diamante,PortaVirus};
	public Blocco[]terreniSpeciale={Pietra,TerrenoFerro};
	public Blocco[]muriSpeciale={MuroPietra,MuroFerro};
	public Blocco[]porteSpeciale={PortaPietra,PortaFerro};
	public Blocco[]guardianiSpeciale={Golem,Zombie};
	public Blocchi()
	{
		for(int a=0;a<blocchi.length;a++)
		{
			blocchi[a].id=(a);
			blocchi[a].ids=String.valueOf(blocchi[a].id);
			blocchi[a].prodotti=blocchi[a].prodotti(this);
		}
	}
	public Blocco trova(CharSequence nome)
	{
		for(Blocco b:blocchi)if(Lista.uguali(b.nomeInglese(),nome))return b;
		return null;
	}
	public int vista=15;
	public int distanza=5;
	public int slot=100;
	public String dimensione(int dimensione)
	{
		if(dimensione==normale)return normales;
		else return quasispazios;
	}
	public int dimensione(String dimensione)
	{
		if(dimensione.equals(normales))return normale;
		else return quasispazio;
	}
	public int sfondoDimensione(int dimensione)
	{
		if(dimensione==quasispazio)return R.drawable.sfondoquasispazio;
		else return R.drawable.sfondo;
	}
	public static boolean battaglia(int suono)
	{
		return suono==R.raw.battle_1||suono==R.raw.battle_2;
	}
	public boolean pace(int suono,int dimensione)
	{
		return (dimensione==normale&&(suono==R.raw.ambient_1||suono==R.raw.ambient_2||suono==R.raw.ambient_3))||(dimensione==quasispazio&&suono==R.raw.quasispazio);
	}
	public static boolean boss(int suono)
	{
		return suono==R.raw.boss_battle;
	}
	public static int battaglia()
	{
		int n=new Random().nextInt(2);
		if(n==0)return R.raw.battle_1;
		else return R.raw.battle_2;
	}
	public int pace(int dimensione)
	{
		if(dimensione==normale)
		{
			int n=new Random().nextInt(3);
			if(n==0)return R.raw.ambient_2;
			else if(n==1)return R.raw.ambient_1;
			else return R.raw.ambient_3;
		}
		else return R.raw.quasispazio;
	}
	public static int boss()
	{
		return R.raw.boss_battle;
	}
}
