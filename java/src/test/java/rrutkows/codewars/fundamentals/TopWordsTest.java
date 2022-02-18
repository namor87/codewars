package rrutkows.codewars.fundamentals;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.stream.*;
import java.util.Arrays;

// TODO: Replace examples and use TDD development by writing your own tests

public class TopWordsTest {

    @Test
    public void sampleTests() {
        assertEquals(Arrays.asList("e", "d", "a"),    TopWords.top3("a a a  b  c c  d d d d  e e e e e"));
        assertEquals(Arrays.asList("e", "ddd", "aa"), TopWords.top3("e e e e DDD ddd DdD: ddd ddd aa aA Aa, bb cc cC e e e"));
        assertEquals(Arrays.asList("won't", "wont"),  TopWords.top3("  //wont won't won't "));
        assertEquals(Arrays.asList("e"),              TopWords.top3("  , e   .. "));
        assertEquals(Arrays.asList(),                 TopWords.top3("  ...  "));
        assertEquals(Arrays.asList(),                 TopWords.top3("  '  "));
        assertEquals(Arrays.asList(),                 TopWords.top3("  '''  "));
        assertEquals(Arrays.asList("a", "of", "on"),  TopWords.top3(Stream
                .of("In a village of La Mancha, the name of which I have no desire to call to",
                        "mind, there lived not long since one of those gentlemen that keep a lance",
                        "in the lance-rack, an old buckler, a lean hack, and a greyhound for",
                        "coursing. An olla of rather more beef than mutton, a salad on most",
                        "nights, scraps on Saturdays, lentils on Fridays, and a pigeon or so extra",
                        "on Sundays, made away with three-quarters of his income.")
                .collect(Collectors.joining("\n")) ));
    }

    @Test
    public void someRandomData() {
        assertEquals(Arrays.asList("vphxjet", "jzlwc", "erfdsyki"),  TopWords.top3(
                "fFmVISdrtG!hssF:hssF EEQDE!hssF hssF!hssF!EEQDE hssF.sIFI EEQDE EEQDE fFmVISdrtG_EEQDE;fFmVISdrtG " +
                        "hssF!fFmVISdrtG/hssF fFmVISdrtG fFmVISdrtG EEQDE?hssF SrKClOXJ!hssF SrKClOXJ;fFmVISdrtG EEQDE " +
                        "EEQDE_EEQDE;sIFI;sIFI/EEQDE hssF sIFI hssF.fFmVISdrtG_sIFI EEQDE hssF EEQDE jHpPNVCx " +
                        "fFmVISdrtG_sIFI-hssF hssF hssF,fFmVISdrtG:EEQDE;jHpPNVCx fFmVISdrtG hssF sIFI hssF jHpPNVCx " +
                        "hssF hssF fFmVISdrtG EEQDE dPMAe.jHpPNVCx,fFmVISdrtG EEQDE fFmVISdrtG_fFmVISdrtG.sIFI sIFI " +
                        "jHpPNVCx fFmVISdrtG!EEQDE;fFmVISdrtG hssF sIFI!fFmVISdrtG hssF,EEQDE-fFmVISdrtG hssF sIFI;" +
                        "hssF jHpPNVCx/fFmVISdrtG.fFmVISdrtG:sIFI sIFI EEQDE jHpPNVCx jHpPNVCx EEQDE fFmVISdrtG hssF " +
                        "hssF-jHpPNVCx sIFI hssF?SrKClOXJ fFmVISdrtG sIFI EEQDE EEQDE-sIFI sIFI fFmVISdrtG sIFI " +
                        "fFmVISdrtG J'Jj vNUA J'Jj aDoiHWlNaR vNUA GSEKW!aDoiHWlNaR-J'Jj GSEKW-WLSdexWi GSEKW " +
                        "WLSdexWi WLSdexWi_UdjDbZcRss vNUA yCrxbAd-yCrxbAd yCrxbAd WLSdexWi!GSEKW,vNUA GSEKW?GSEKW " +
                        "WLSdexWi yCrxbAd yCrxbAd.aDoiHWlNaR_yCrxbAd UdjDbZcRss WLSdexWi vNUA vNUA vNUA J'Jj;J'Jj " +
                        "aDoiHWlNaR?GSEKW yCrxbAd WLSdexWi:aDoiHWlNaR-UdjDbZcRss_WLSdexWi WLSdexWi,WLSdexWi " +
                        "aDoiHWlNaR J'Jj yCrxbAd.aDoiHWlNaR-GSEKW,aDoiHWlNaR.vNUA GSEKW_yCrxbAd vNUA.UdjDbZcRss " +
                        "GSEKW?J'Jj WLSdexWi WLSdexWi.aDoiHWlNaR;aDoiHWlNaR_GSEKW/GSEKW J'Jj GSEKW WLSdexWi?GSEKW " +
                        "UdjDbZcRss yCrxbAd!aDoiHWlNaR aDoiHWlNaR:J'Jj;cSmfQEXtYn_GSEKW GSEKW!yCrxbAd WLSdexWi-J'Jj;" +
                        "GSEKW WLSdexWi/J'Jj!vNUA/J'Jj yCrxbAd vNUA;yCrxbAd yCrxbAd J'Jj vNUA J'Jj:TxiUFwBVZ,J'Jj " +
                        "yCrxbAd WLSdexWi vNUA aDoiHWlNaR yCrxbAd:WLSdexWi vNUA vNUA-UdjDbZcRss:aDoiHWlNaR J'Jj " +
                        "yCrxbAd yCrxbAd/cSmfQEXtYn yCrxbAd J'Jj,yCrxbAd GSEKW aDoiHWlNaR GSEKW-GSEKW/J'Jj " +
                        "WLSdexWi!UdjDbZcRss?J'Jj UdjDbZcRss GSEKW aDoiHWlNaR?aDoiHWlNaR,GSEKW aDoiHWlNaR!UdjDbZcRss " +
                        "GSEKW aDoiHWlNaR yCrxbAd-J'Jj yCrxbAd aDoiHWlNaR?WLSdexWi;WLSdexWi:GSEKW:vNUA;vNUA " +
                        "GSEKW:cSmfQEXtYn?yCrxbAd aDoiHWlNaR/GSEKW aDoiHWlNaR yCrxbAd cSmfQEXtYn aDoiHWlNaR_" +
                        "UdjDbZcRss:J'Jj_J'Jj.WLSdexWi J'Jj hbexNqG-tCBn StdzXLjR JZlwc!vkjEqntVkC?VpHxjEt!isnoyrt " +
                        "AOxFSQgbEj erFDSykI,VpHxjEt:JZlwc?JZlwc isnoyrt:tCBn AOxFSQgbEj zTM,VpHxjEt-vkjEqntVkC." +
                        "dyScDvDc vkjEqntVkC dkituPe tCBn AOxFSQgbEj/isnoyrt VpHxjEt:DWWWXVh tCBn zTM;AOxFSQgbEj " +
                        "htdvPmt_dkituPe JZlwc!VpHxjEt;vkjEqntVkC,zTM erFDSykI/AOxFSQgbEj isnoyrt;dkituPe/JZlwc/tCBn," +
                        "zTM;vkjEqntVkC zTM tCBn isnoyrt DWWWXVh;erFDSykI_dyScDvDc.erFDSykI?dkituPe/AOxFSQgbEj " +
                        "StdzXLjR erFDSykI.isnoyrt.htdvPmt JZlwc!hbexNqG,JZlwc?DWWWXVh:tCBn JsIkqI!tCBn.dkituPe " +
                        "isnoyrt erFDSykI DWWWXVh zTM_VpHxjEt!JZlwc htdvPmt;htdvPmt;JsIkqI dkituPe?hbexNqG " +
                        "hbexNqG;StdzXLjR AOxFSQgbEj WwSdduD dkituPe StdzXLjR.erFDSykI erFDSykI.vkjEqntVkC_VpHxjEt " +
                        "JZlwc,zTM-AOxFSQgbEj WwSdduD M'IjDILJgf AOxFSQgbEj?WwSdduD_dyScDvDc erFDSykI/dkituPe " +
                        "WwSdduD:dyScDvDc dyScDvDc vkjEqntVkC:vkjEqntVkC;AOxFSQgbEj?AOxFSQgbEj htdvPmt tCBn erFDSykI_" +
                        "JZlwc;tCBn JsIkqI/erFDSykI_dkituPe DWWWXVh,vkjEqntVkC dkituPe:StdzXLjR/htdvPmt htdvPmt tCBn " +
                        "JZlwc-AOxFSQgbEj:DWWWXVh zTM VpHxjEt tCBn DWWWXVh isnoyrt erFDSykI!dyScDvDc.StdzXLjR." +
                        "dkituPe StdzXLjR-isnoyrt VpHxjEt dyScDvDc;isnoyrt;erFDSykI tCBn htdvPmt AOxFSQgbEj-isnoyrt " +
                        "htdvPmt dyScDvDc.zTM erFDSykI AOxFSQgbEj JZlwc;htdvPmt!JsIkqI VpHxjEt;erFDSykI dkituPe-" +
                        "dkituPe zTM htdvPmt,isnoyrt,WwSdduD_htdvPmt AOxFSQgbEj isnoyrt!JsIkqI JZlwc JsIkqI tCBn " +
                        "dkituPe?VpHxjEt JsIkqI?AOxFSQgbEj StdzXLjR DWWWXVh StdzXLjR htdvPmt VpHxjEt:AOxFSQgbEj " +
                        "VpHxjEt dyScDvDc AOxFSQgbEj vkjEqntVkC;DWWWXVh JZlwc dkituPe JZlwc-erFDSykI vkjEqntVkC-" +
                        "VpHxjEt dkituPe_htdvPmt WwSdduD,tCBn DWWWXVh_tCBn/VpHxjEt erFDSykI!isnoyrt hbexNqG JsIkqI!" +
                        "JZlwc;isnoyrt tCBn BNYUqcmH_htdvPmt?htdvPmt,dkituPe zTM erFDSykI;vkjEqntVkC-JsIkqI;isnoyrt " +
                        "DWWWXVh-AOxFSQgbEj vkjEqntVkC dkituPe tCBn htdvPmt?VpHxjEt;tCBn isnoyrt VpHxjEt.isnoyrt " +
                        "erFDSykI_erFDSykI tCBn htdvPmt:isnoyrt VpHxjEt erFDSykI!VpHxjEt hbexNqG?StdzXLjR/erFDSykI:" +
                        "htdvPmt AOxFSQgbEj dyScDvDc JZlwc_VpHxjEt-JZlwc JZlwc JZlwc erFDSykI_DWWWXVh.vkjEqntVkC " +
                        "isnoyrt.JZlwc_hbexNqG JZlwc/vkjEqntVkC dkituPe;VpHxjEt:erFDSykI dkituPe WwSdduD/WwSdduD " +
                        "DWWWXVh/AOxFSQgbEj;erFDSykI WwSdduD!erFDSykI-dyScDvDc/AOxFSQgbEj:WwSdduD StdzXLjR M'" +
                        "IjDILJgf JZlwc isnoyrt VpHxjEt JZlwc_isnoyrt.JZlwc htdvPmt vkjEqntVkC tCBn_DWWWXVh!JZlwc " +
                        "VpHxjEt?htdvPmt/JZlwc VpHxjEt;WwSdduD isnoyrt dkituPe WwSdduD VpHxjEt.DWWWXVh:vkjEqntVkC " +
                        "WwSdduD-tCBn VpHxjEt AOxFSQgbEj tCBn_erFDSykI?zTM?StdzXLjR;tCBn htdvPmt!isnoyrt VpHxjEt " +
                        "JsIkqI VpHxjEt htdvPmt VpHxjEt JZlwc!"));
    }

    @Test
    public void someRandomData2() {
        assertEquals(Arrays.asList("g't'ue", "w'qhhbmf", "rtj"),
                TopWords.top3(Stream.of(
                        "xmaCsApM.LRslYLJMOs OalK OalK?JVg?OalK UuTVx UuTVx Rtj Rtj,zQuznxJTMI zQuznxJTMI Rtj:Rtj?Rtj zQuznxJTMI Rtj Rtj Rtj zQuznxJTMI-zQuznxJTMI Rtj/Rtj,zQuznxJTMI?zQuznxJTMI-Rtj zQuznxJTMI zQuznxJTMI Rtj zQuznxJTMI-Rtj;Rtj.zQuznxJTMI zQuznxJTMI!Rtj Rtj Rtj zQuznxJTMI:zQuznxJTMI zQuznxJTMI,Rtj zQuznxJTMI Rtj Rtj Rtj zQuznxJTMI-Rtj,Apf g'T'Ue w'qhhbmf w'qhhbmf,vWi'rnGsEE g'T'Ue w'qhhbmf!tGPK Oviof'A/g'T'Ue w'qhhbmf_zczHa iwyPTf vWi'rnGsEE,Oviof'A?g'T'Ue Apf!w'qhhbmf tGPK zKvkcksL!g'T'Ue Apf?DjzjyCAZ_i" ,
                        "wyPTf w'qhhbmf w'qhhbmf:tGPK zKvkcksL iwyPTf-w'qhhbmf/DjzjyCAZ Apf w'qhhbmf/tGPK:zKvkcksL;g'T'Ue/g'T'Ue_g'T'Ue-Apf iwyPTf g'T'Ue Apf_tGPK-g'T'Ue w'qhhbmf?g'T'Ue tGPK vWi'rnGsEE Oviof'A DjzjyCAZ CpjgGP Apf vWi'rnGsEE;w'qhhbmf/DjzjyCAZ g'T'Ue wSoy Apf.Apf iwyPTf!Apf.w'qhhbmf!DjzjyCAZ:w'qhhbmf!w'qhhbmf;DjzjyCAZ?iwyPTf:zczHa/zKvkcksL_CpjgGP g'T'Ue zKvkcksL tGPK tGPK vWi'rnGsEE g'T'Ue?Oviof'A DjzjyCAZ g'T'Ue w'qhhbmf?tGPK?w'qhhbmf_tGPK;Oviof'A g'T'Ue zczHa zKvkcksL tGPK tGPK w'qhhbmf?Apf g'T'Ue;Cpjg" ,
                        "GP iwyPTf;zKvkcksL,iwyPTf!w'qhhbmf?zKvkcksL.tGPK:w'qhhbmf tGPK_DjzjyCAZ g'T'Ue g'T'Ue Apf_w'qhhbmf w'qhhbmf vWi'rnGsEE;zKvkcksL g'T'Ue DjzjyCAZ-w'qhhbmf g'T'Ue w'qhhbmf;iwyPTf CpjgGP g'T'Ue wSoy g'T'Ue-g'T'Ue;vWi'rnGsEE iwyPTf.DjzjyCAZ Apf?vWi'rnGsEE tGPK/vWi'rnGsEE/g'T'Ue_iwyPTf,zKvkcksL!iwyPTf Apf g'T'Ue?g'T'Ue g'T'Ue w'qhhbmf,DjzjyCAZ vWi'rnGsEE g'T'Ue/CpjgGP Apf")
                    .collect(Collectors.joining("\n"))));
    }

}