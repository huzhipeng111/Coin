package com.huzhipeng.coin.entity.huobi;

import java.util.ArrayList;
import java.util.List;

public class HuobiTick {

    /**
     * ch : market.tickers
     * status : ok
     * ts : 1572574179460
     * data : [{"symbol":"smtusdt","open":0.004452,"high":0.004614,"low":0.0043,"close":0.004332,"amount":3.842441400825228E7,"vol":174272.6528075456,"count":6366},{"symbol":"yeeeth","open":8.75E-6,"high":9.01E-6,"low":8.41E-6,"close":8.5E-6,"amount":2.026248766881316E7,"vol":174.63430078147627,"count":10677},{"symbol":"atombtc","open":3.5106E-4,"high":3.6048E-4,"low":3.4808E-4,"close":3.5124E-4,"amount":110966.77876503106,"vol":38.5899517312,"count":5391},{"symbol":"aidoceth","open":1.833E-5,"high":1.872E-5,"low":1.789E-5,"close":1.802E-5,"amount":2315637.72,"vol":42.4246851963,"count":4269},{"symbol":"htbtc","open":4.2861E-4,"high":4.32E-4,"low":4.222E-4,"close":4.3016E-4,"amount":1721597.2268812037,"vol":730.0484323829378,"count":18522},{"symbol":"iostht","open":0.00174295,"high":0.00186934,"low":0.0017398,"close":0.00177925,"amount":1.7958015524271335E7,"vol":30626.0619439761,"count":8783},{"symbol":"dashht","open":17.966817,"high":18.508803,"low":17.961115,"close":18.317729,"amount":646.9323,"vol":11906.0988617075,"count":5689},{"symbol":"iostusdt","open":0.00688,"high":0.007356,"low":0.00685,"close":0.006974,"amount":1.1826767659034941E9,"vol":7941174.54539125,"count":69903},{"symbol":"qunbtc","open":7.658E-7,"high":8.0E-7,"low":7.521E-7,"close":7.661E-7,"amount":1.6716326849573698E7,"vol":12.842645651235,"count":12678},{"symbol":"edueth","open":7.83E-7,"high":9.913E-7,"low":7.368E-7,"close":7.61E-7,"amount":1.8433435263388816E8,"vol":148.962926014443,"count":5215},{"symbol":"rcneth","open":2.0849E-4,"high":2.2684E-4,"low":2.068E-4,"close":2.2308E-4,"amount":71591,"vol":15.3674978,"count":4129},{"symbol":"waxpeth","open":1.49E-4,"high":1.51E-4,"low":1.38E-4,"close":1.46E-4,"amount":202451.84347316003,"vol":29.3882037154,"count":3354},{"symbol":"bchbtc","open":0.030588,"high":0.0312,"low":0.030181,"close":0.030847,"amount":46694.873,"vol":1443.1384955861,"count":19249},{"symbol":"wanbtc","open":2.484E-5,"high":2.555E-5,"low":2.459E-5,"close":2.469E-5,"amount":1789315.102100977,"vol":43.9707332426,"count":11408},{"symbol":"lambbtc","open":4.78E-6,"high":5.8E-6,"low":4.75E-6,"close":5.2E-6,"amount":6.440346502426214E7,"vol":317.36502390828804,"count":31265},{"symbol":"waxpbtc","open":2.87E-6,"high":2.93E-6,"low":2.73E-6,"close":2.88E-6,"amount":2836566.180298158,"vol":8.080106913817,"count":4474},{"symbol":"lxtusdt","open":0.006206,"high":0.006859,"low":0.0057,"close":0.006205,"amount":3.9361563215067095E8,"vol":2284794.8328659716,"count":22912},{"symbol":"pceth","open":1.39E-6,"high":1.39E-6,"low":1.29E-6,"close":1.31E-6,"amount":1.378154821146674E8,"vol":176.695817744516,"count":4466},{"symbol":"xvgeth","open":2.047E-5,"high":2.057E-5,"low":2.021E-5,"close":2.029E-5,"amount":653203.38,"vol":13.3522948479,"count":5462},{"symbol":"propyeth","open":6.3479E-4,"high":6.4429E-4,"low":5.8741E-4,"close":6.2483E-4,"amount":33177.68,"vol":19.9262309732,"count":5538},{"symbol":"forbtc","open":3.77E-6,"high":4.03E-6,"low":3.77E-6,"close":3.94E-6,"amount":744346,"vol":2.8917582232,"count":4929},{"symbol":"zrxbtc","open":2.964E-5,"high":3.048E-5,"low":2.96E-5,"close":3.035E-5,"amount":182311.48992966887,"vol":5.4798377836,"count":3329},{"symbol":"btmusdt","open":0.1113,"high":0.116,"low":0.1081,"close":0.1098,"amount":4.604817600761182E7,"vol":5281296.027962668,"count":19901},{"symbol":"faireth","open":1.731E-5,"high":1.804E-5,"low":1.582E-5,"close":1.692E-5,"amount":2.229701496660536E7,"vol":387.1304599303,"count":17929},{"symbol":"nanoeth","open":0.004712,"high":0.004791,"low":0.00465,"close":0.004675,"amount":3114.3807791727904,"vol":14.7111102653,"count":5256},{"symbol":"yeebtc","open":1.713E-7,"high":1.749E-7,"low":1.651E-7,"close":1.69E-7,"amount":8.664605825E7,"vol":14.886259634583,"count":12478},{"symbol":"acteth","open":5.207E-5,"high":5.486E-5,"low":5.077E-5,"close":5.113E-5,"amount":2545996.91,"vol":131.7437494631,"count":5814},{"symbol":"batusdt","open":0.2302,"high":0.2324,"low":0.228,"close":0.2313,"amount":329691.9375237637,"vol":77361.65207113,"count":4411},{"symbol":"yccbtc","open":1.5526E-6,"high":1.5531E-6,"low":1.4293E-6,"close":1.516E-6,"amount":1.3051964243238024E7,"vol":18.997878538439103,"count":9569},{"symbol":"xzcusdt","open":4.9489,"high":5.0323,"low":4.8844,"close":4.9119,"amount":4466.443900180831,"vol":22050.84286785,"count":10334},{"symbol":"sntusdt","open":0.013,"high":0.013493,"low":0.012877,"close":0.013311,"amount":1.4691995845020315E7,"vol":191693.62668560102,"count":4069},{"symbol":"boxbtc","open":4.85E-7,"high":6.298E-7,"low":4.83E-7,"close":5.74E-7,"amount":1.6573482451628322E7,"vol":8.929607984998624,"count":6032},{"symbol":"naseth","open":0.00323,"high":0.003269,"low":0.003185,"close":0.003247,"amount":22886.4958,"vol":74.362225826,"count":4219},{"symbol":"ctxceth","open":4.2964E-4,"high":4.6672E-4,"low":4.1761E-4,"close":4.2214E-4,"amount":1073019.42,"vol":455.1340505221,"count":5060},{"symbol":"bcvbtc","open":3.8874E-6,"high":3.9311E-6,"low":3.6647E-6,"close":3.7727E-6,"amount":4.624297102925661E7,"vol":174.86068009580774,"count":4472},{"symbol":"ektusdt","open":0.1307,"high":0.1322,"low":0.129,"close":0.129,"amount":1.6755118745175062E7,"vol":2199406.418378954,"count":20096},{"symbol":"cvcoineth","open":3.85E-4,"high":4.02E-4,"low":3.64E-4,"close":3.82E-4,"amount":130177.5926,"vol":49.3018310441,"count":4260},{"symbol":"topceth","open":3.903E-5,"high":3.904E-5,"low":3.842E-5,"close":3.874E-5,"amount":5002217.8947932115,"vol":193.40513634484702,"count":6103},{"symbol":"xlmusdt","open":0.063416,"high":0.065505,"low":0.063157,"close":0.065497,"amount":1.9179781589348283E7,"vol":1222327.9380348476,"count":10959},{"symbol":"ttbtc","open":1.27E-6,"high":1.8E-6,"low":1.21E-6,"close":1.61E-6,"amount":1.2986046379726338E8,"vol":175.87869162472785,"count":11260},{"symbol":"vsysbtc","open":7.26E-6,"high":7.45E-6,"low":7.05E-6,"close":7.23E-6,"amount":1136516.07,"vol":8.2851901773,"count":9447},{"symbol":"shebtc","open":8.6E-8,"high":1.05E-7,"low":8.6E-8,"close":8.9E-8,"amount":2.4587278547510943E8,"vol":22.169167834859003,"count":3722},{"symbol":"lsketh","open":0.004356,"high":0.004408,"low":0.004336,"close":0.00435,"amount":2485.9754,"vol":10.814627715,"count":5123},{"symbol":"qtumeth","open":0.01171,"high":0.011904,"low":0.011527,"close":0.011571,"amount":57204.69906423213,"vol":676.1175671126,"count":6952},{"symbol":"linkusdt","open":2.6057,"high":2.7917,"low":2.6027,"close":2.7345,"amount":564735.9259284826,"vol":1509364.0346166636,"count":12852},{"symbol":"bhtusdt","open":0.044013,"high":0.044718,"low":0.043916,"close":0.044254,"amount":2233861.560673982,"vol":98158.2734972674,"count":8362},{"symbol":"lambusdt","open":0.043939,"high":0.050534,"low":0.043798,"close":0.047393,"amount":2.221202494077476E8,"vol":9859303.746617723,"count":66986},{"symbol":"pvtbtc","open":7.3E-8,"high":7.3E-8,"low":6.6E-8,"close":7.0E-8,"amount":1.388708888660537E8,"vol":10.067740972741,"count":5157},{"symbol":"xrpusdt","open":0.29324,"high":0.29508,"low":0.2909,"close":0.29259,"amount":5.6350044027008764E7,"vol":1.6517379607723244E7,"count":39005},{"symbol":"seeleusdt","open":0.066147,"high":0.06684,"low":0.0655,"close":0.066187,"amount":3.0788911141472697E7,"vol":2047837.5880801384,"count":6895},{"symbol":"ncashbtc","open":1.01E-7,"high":1.038E-7,"low":9.96E-8,"close":1.02E-7,"amount":1.2944134463317757E7,"vol":1.320095546323,"count":5799},{"symbol":"newbtc","open":3.9E-7,"high":4.2E-7,"low":3.9E-7,"close":4.2E-7,"amount":1.666654869E7,"vol":6.6530104598,"count":466},{"symbol":"propybtc","open":1.225E-5,"high":1.235E-5,"low":1.145E-5,"close":1.233E-5,"amount":1115299.4531771021,"vol":13.254934451367651,"count":8582},{"symbol":"hb10usdt","open":0.5127,"high":0.5213,"low":0.5042,"close":0.5085,"amount":1332658.4254665,"vol":683118.93491288,"count":19695},{"symbol":"atpht","open":0.002229,"high":0.002507,"low":0.002119,"close":0.00219,"amount":352894.58,"vol":818.96768634,"count":8017},{"symbol":"nulseth","open":0.002293,"high":0.002358,"low":0.002259,"close":0.002272,"amount":14474.269383118557,"vol":33.5568639474,"count":5526},{"symbol":"storjbtc","open":1.498E-5,"high":1.52E-5,"low":1.491E-5,"close":1.507E-5,"amount":54501.43,"vol":0.8249984216,"count":1338},{"symbol":"vsysht","open":0.016987,"high":0.017478,"low":0.016546,"close":0.016948,"amount":250852.99,"vol":4327.85344261,"count":9652},{"symbol":"uuubtc","open":8.4E-8,"high":8.83E-8,"low":8.0E-8,"close":8.57E-8,"amount":1.0566238899297842E8,"vol":8.725425553075212,"count":5095},{"symbol":"ctxcusdt","open":0.0782,"high":0.0798,"low":0.0754,"close":0.076,"amount":2878479.5816187854,"vol":221654.50226023444,"count":6100},{"symbol":"bixusdt","open":0.159286,"high":0.162126,"low":0.15718,"close":0.158608,"amount":585491.0488,"vol":91862.3804987613,"count":4913},{"symbol":"kncbtc","open":1.892E-5,"high":1.944E-5,"low":1.883E-5,"close":1.923E-5,"amount":85675.4445020747,"vol":1.62644942,"count":3611},{"symbol":"icxbtc","open":1.77E-5,"high":1.868E-5,"low":1.76E-5,"close":1.781E-5,"amount":228923.9105,"vol":4.094986509423,"count":4940},{"symbol":"nasusdt","open":0.5866,"high":0.6,"low":0.5776,"close":0.5904,"amount":1954042.4229498156,"vol":1157648.83910783,"count":13603},{"symbol":"bkbtbtc","open":4.6E-8,"high":7.62E-8,"low":4.56E-8,"close":6.79E-8,"amount":5.952529515108209E8,"vol":37.80780014346176,"count":7675},{"symbol":"swftceth","open":6.42E-6,"high":6.6E-6,"low":6.35E-6,"close":6.47E-6,"amount":6285410.92,"vol":40.2589667398,"count":5769},{"symbol":"ltcbtc","open":0.006361,"high":0.00645,"low":0.00632,"close":0.006357,"amount":48450.7546,"vol":308.479637252,"count":8379},{"symbol":"itcusdt","open":0.1309,"high":0.1483,"low":0.1283,"close":0.132,"amount":3786021.514646066,"vol":502441.8889059384,"count":11891},{"symbol":"fsnusdt","open":0.64,"high":0.64,"low":0.5626,"close":0.6046,"amount":988255.3398887941,"vol":619178.5037712421,"count":13333},{"symbol":"iiceth","open":1.1244E-6,"high":1.8533E-6,"low":1.06E-6,"close":1.276E-6,"amount":3.5104847004596263E8,"vol":424.335685182042,"count":4783},{"symbol":"utketh","open":1.0888E-4,"high":1.0911E-4,"low":1.0404E-4,"close":1.0574E-4,"amount":7166780.13,"vol":765.5928937481,"count":5952},{"symbol":"oneusdt","open":0.004859,"high":0.00495,"low":0.00455,"close":0.004772,"amount":2.8025986604194667E7,"vol":133755.21340807813,"count":7027},{"symbol":"cmtusdt","open":0.019974,"high":0.020142,"low":0.0195,"close":0.019824,"amount":1.4467678925155671E7,"vol":291868.12727533013,"count":9151},{"symbol":"skmht","open":6.94E-4,"high":7.32E-4,"low":6.48E-4,"close":6.85E-4,"amount":1410563.19,"vol":949.18039334,"count":7636},{"symbol":"qashbtc","open":6.8E-6,"high":6.89E-6,"low":6.67E-6,"close":6.87E-6,"amount":130690.59313888071,"vol":0.881931725914,"count":3546},{"symbol":"wavesusdt","open":0.7861,"high":0.7892,"low":0.7722,"close":0.7762,"amount":93914.10659359614,"vol":73142.0567142,"count":5588},{"symbol":"wicceth","open":7.5902E-4,"high":7.7641E-4,"low":7.3808E-4,"close":7.4983E-4,"amount":25824.96,"vol":19.3631384329,"count":5064},{"symbol":"seeleeth","open":3.6457E-4,"high":3.7047E-4,"low":3.586E-4,"close":3.6422E-4,"amount":8524477.94917679,"vol":3141.2674000867,"count":6031},{"symbol":"ethusdt","open":181.69,"high":184.25,"low":179.8,"close":181.94,"amount":447749.4746195343,"vol":8.133825291667122E7,"count":118795},{"symbol":"dateth","open":6.54E-6,"high":6.76E-6,"low":6.45E-6,"close":6.48E-6,"amount":3.870644096E7,"vol":250.5031167211,"count":5517},{"symbol":"buteth","open":1.351E-5,"high":1.354E-5,"low":1.253E-5,"close":1.343E-5,"amount":2.0966581763257574E7,"vol":278.726042193,"count":7254},{"symbol":"bixbtc","open":1.727E-5,"high":1.756E-5,"low":1.722E-5,"close":1.749E-5,"amount":140281.65,"vol":2.4251470975,"count":3502},{"symbol":"xrpbtc","open":3.181E-5,"high":3.235E-5,"low":3.169E-5,"close":3.212E-5,"amount":1.0075544931264848E7,"vol":322.9206498,"count":8144},{"symbol":"aeusdt","open":0.2131,"high":0.2215,"low":0.2117,"close":0.2142,"amount":1378451.9402012157,"vol":292958.27608102,"count":6564},{"symbol":"ttusdt","open":0.011698,"high":0.0163,"low":0.011213,"close":0.014653,"amount":6.306236645132414E8,"vol":7791065.774509226,"count":49423},{"symbol":"htusdt","open":3.95,"high":3.9584,"low":3.8516,"close":3.9183,"amount":9910742.442490956,"vol":3.8598005219658084E7,"count":82273},{"symbol":"getbtc","open":8.723E-7,"high":1.0578E-6,"low":7.138E-7,"close":8.25E-7,"amount":1.845945053391123E8,"vol":163.98933733684484,"count":29158},{"symbol":"waveseth","open":0.004341,"high":0.004354,"low":0.00424,"close":0.004252,"amount":3987.081945790873,"vol":17.0836972361,"count":5247},{"symbol":"qasheth","open":3.43E-4,"high":3.48E-4,"low":3.39E-4,"close":3.43E-4,"amount":28298.65710979249,"vol":9.5996734057,"count":4527},{"symbol":"omgusdt","open":0.9471,"high":0.96,"low":0.9347,"close":0.9407,"amount":2543021.5863958374,"vol":2395332.39033193,"count":7101},{"symbol":"btcusdt","open":9212.09,"high":9266.02,"low":9003.88,"close":9107.7,"amount":38500.73361625262,"vol":3.526631808299436E8,"count":354204},{"symbol":"emusdt","open":0.015163,"high":0.015698,"low":0.013,"close":0.013061,"amount":1.665967358868732E8,"vol":2544086.274303496,"count":24863},{"symbol":"uceth","open":1.434E-6,"high":1.5176E-6,"low":1.349E-6,"close":1.372E-6,"amount":2.5882662701E8,"vol":362.465153411594,"count":4111},{"symbol":"etcbtc","open":5.19E-4,"high":5.32E-4,"low":5.18E-4,"close":5.27E-4,"amount":225667.075,"vol":119.8767133066,"count":4690},{"symbol":"ftieth","open":2.75E-6,"high":2.98E-6,"low":2.66E-6,"close":2.92E-6,"amount":1.0383270556267911E8,"vol":270.886735557,"count":5234},{"symbol":"mxbtc","open":1.043E-5,"high":2.499E-5,"low":1.043E-5,"close":2.112E-5,"amount":683629.99,"vol":15.389734771787818,"count":562},{"symbol":"nodebtc","open":1.471E-6,"high":1.53E-6,"low":1.4511E-6,"close":1.478E-6,"amount":1.7492903055297203E7,"vol":25.793845006822,"count":7104},{"symbol":"geteth","open":4.335E-5,"high":5.329E-5,"low":3.601E-5,"close":4.161E-5,"amount":5.694867553742499E7,"vol":2491.9375302372996,"count":23948},{"symbol":"mxusdt","open":0.095,"high":0.2179,"low":0.095,"close":0.1926,"amount":3593246.558737316,"vol":731802.6188230293,"count":3396},{"symbol":"wtceth","open":0.004002,"high":0.004023,"low":0.003883,"close":0.003906,"amount":102764.83303203256,"vol":422.5528442092,"count":5714},{"symbol":"arpausdt","open":0.016104,"high":0.016715,"low":0.01599,"close":0.016526,"amount":2.7240696942970924E7,"vol":444054.2209896254,"count":9834},{"symbol":"nccbtc","open":3.07E-7,"high":3.22E-7,"low":2.6E-7,"close":3.02E-7,"amount":1.880693846223059E7,"vol":5.663276344899,"count":4703},{"symbol":"hthusd","open":3.9574,"high":3.9606,"low":3.8634,"close":3.917,"amount":320468.43231864856,"vol":1250083.03517408,"count":10545},{"symbol":"elfbtc","open":9.59E-6,"high":1.0E-5,"low":9.59E-6,"close":9.86E-6,"amount":376907.08071590477,"vol":3.62778608,"count":3480},{"symbol":"portaleth","open":1.187E-5,"high":1.209E-5,"low":1.088E-5,"close":1.122E-5,"amount":3.361692508562327E7,"vol":401.7906381552,"count":5333},{"symbol":"covaeth","open":2.1E-6,"high":2.22E-6,"low":2.01E-6,"close":2.15E-6,"amount":5.455847290451251E7,"vol":111.396447768595,"count":5338},{"symbol":"newusdt","open":0.003637,"high":0.003699,"low":0.003575,"close":0.00363,"amount":1.3354022003166687E8,"vol":489355.737731141,"count":4794},{"symbol":"zilusdt","open":0.005512,"high":0.006185,"low":0.005475,"close":0.006126,"amount":1.540065544901032E8,"vol":890709.3523565647,"count":13329},{"symbol":"abteth","open":8.4282E-4,"high":8.5921E-4,"low":8.3045E-4,"close":8.4501E-4,"amount":56116.85,"vol":47.4976615301,"count":5239},{"symbol":"rcccbtc","open":2.03E-6,"high":2.06E-6,"low":1.98E-6,"close":2.02E-6,"amount":5349410.9235105375,"vol":10.7381633926,"count":3809},{"symbol":"bchusdt","open":281.8,"high":285.93,"low":277.67,"close":281.17,"amount":266671.3896843656,"vol":7.516846006773259E7,"count":114076},{"symbol":"triobtc","open":2.22E-7,"high":2.38E-7,"low":2.22E-7,"close":2.37E-7,"amount":2.9765381081666E7,"vol":6.64389229111,"count":13124},{"symbol":"ruffeth","open":4.84E-5,"high":5.151E-5,"low":4.668E-5,"close":4.725E-5,"amount":1513115.27,"vol":71.5545610361,"count":5707},{"symbol":"fttbtc","open":1.5281E-4,"high":1.548E-4,"low":1.5066E-4,"close":1.508E-4,"amount":969525.2182228247,"vol":147.73682154219946,"count":6268},{"symbol":"fairbtc","open":3.351E-7,"high":3.49E-7,"low":3.102E-7,"close":3.348E-7,"amount":5.631909071237786E7,"vol":19.687244937835906,"count":17773},{"symbol":"thetausdt","open":0.0935,"high":0.095,"low":0.0923,"close":0.0927,"amount":1467678.2422219838,"vol":136448.22090769,"count":4564},{"symbol":"ftibtc","open":5.48E-8,"high":5.97E-8,"low":5.3E-8,"close":5.85E-8,"amount":2.1382737872169054E8,"vol":11.26452536686148,"count":4927},{"symbol":"mxht","open":0.025,"high":0.055,"low":0.025,"close":0.048857,"amount":1076138.5353183637,"vol":55172.92457196,"count":2157},{"symbol":"asteth","open":1.3416E-4,"high":1.3669E-4,"low":1.3358E-4,"close":1.3597E-4,"amount":58842.69,"vol":7.9315179998,"count":2891},{"symbol":"fsnht","open":0.167617,"high":0.17773,"low":0.144941,"close":0.156922,"amount":24762.6055,"vol":4023.1073536796,"count":7314},{"symbol":"cnnsbtc","open":6.8E-7,"high":7.0E-7,"low":6.8E-7,"close":6.9E-7,"amount":1346774.33,"vol":0.932648287,"count":3381},{"symbol":"renusdt","open":0.049138,"high":0.0505,"low":0.048296,"close":0.050002,"amount":445177.60312475247,"vol":22211.55876831,"count":4091},{"symbol":"aidocbtc","open":3.62E-7,"high":3.756E-7,"low":3.57E-7,"close":3.635E-7,"amount":3.0167510739334397E7,"vol":10.897178968181,"count":9266},{"symbol":"xzceth","open":0.027226,"high":0.027389,"low":0.026565,"close":0.027016,"amount":622.7234,"vol":16.8585918033,"count":9085},{"symbol":"zrxeth","open":0.00150417,"high":0.00162916,"low":0.00148667,"close":0.0015167,"amount":41216.40338236911,"vol":62.486741751,"count":4068},{"symbol":"lxtbtc","open":6.797E-7,"high":7.501E-7,"low":6.188E-7,"close":6.779E-7,"amount":1.1613354768999214E8,"vol":74.25522326528667,"count":12859},{"symbol":"srneth","open":5.063E-5,"high":5.21E-5,"low":4.935E-5,"close":5.076E-5,"amount":1959213.554212634,"vol":97.2820492835,"count":5051}]
     */

    private String ch;
    private String status;
    private long ts;
    private ArrayList<DataBean> data;

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * symbol : smtusdt
         * open : 0.004452
         * high : 0.004614
         * low : 0.0043
         * close : 0.004332
         * amount : 3.842441400825228E7
         * vol : 174272.6528075456
         * count : 6366
         */

        private String symbol;
        private double open;
        private double high;
        private double low;
        private double close;
        private double amount;
        private double vol;
        private int count;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public double getOpen() {
            return open;
        }

        public void setOpen(double open) {
            this.open = open;
        }

        public double getHigh() {
            return high;
        }

        public void setHigh(double high) {
            this.high = high;
        }

        public double getLow() {
            return low;
        }

        public void setLow(double low) {
            this.low = low;
        }

        public double getClose() {
            return close;
        }

        public void setClose(double close) {
            this.close = close;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getVol() {
            return vol;
        }

        public void setVol(double vol) {
            this.vol = vol;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
