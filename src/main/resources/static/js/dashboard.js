let labels = [];
let data = [];
const colors = [
    "#b695ff", "#f4aaa4", "#8feac5", "#ffcc66", "#a3a3c2",
    "#ff9999", "#66cccc", "#ff6666", "#99ccff", "#ffcc99",
    "#99ffcc", "#ff9999", "#66cccc", "#ff6666", "#99ccff",
    "#ffcc99", "#99ffcc", "#ff9999", "#66cccc", "#ff6666"
];

async function getProjectInvestmentSum() {
    await axios.get('get-project-investment-sum').then(res => {
        const datasets = res.data;
        datasets.forEach(dataset => {
            labels.push(dataset[0].nom);
            data.push(dataset[1]);
        });
    }).catch(error => {
        console.error('Error fetching data:', error);
    });
}

const doughnutChartData = {
    labels: labels,
    dataUnit: 'DHS',
    legend: false,
    datasets: [{
        borderColor: "#fff",
        background: colors,
        data: data
    }]
};

function doughnutChart(selector, set_data) {

    var $selector = (selector) ? $(selector) : $('.doughnut-chart');
    $selector.each(function () {
        var $self = $(this), _self_id = $self.attr('id'),
            _get_data = (typeof set_data === 'undefined') ? eval(_self_id) : set_data;
        var selectCanvas = document.getElementById(_self_id).getContext("2d");
        var chart_data = [];

        for (var i = 0; i < _get_data.datasets.length; i++) {
            chart_data.push({
                backgroundColor: _get_data.datasets[i].background,
                borderWidth: 2,
                borderColor: _get_data.datasets[i].borderColor,
                hoverBorderColor: _get_data.datasets[i].borderColor,
                data: _get_data.datasets[i].data,
            });
        }
        var chart = new Chart(selectCanvas, {
            type: 'doughnut',
            data: {
                labels: _get_data.labels,
                datasets: chart_data,
            },
            options: {
                plugins: {
                    legend: {
                        display: (_get_data.legend) ? _get_data.legend : true,
                        rtl: NioApp.State.isRTL,
                        labels: {
                            boxWidth: 12,
                            padding: 20,
                            color: '#6783b8',
                        }
                    },
                    tooltip: {
                        enabled: true,
                        rtl: NioApp.State.isRTL,
                        callbacks: {
                            label: function (context) {
                                return `${context.parsed} ${_get_data.dataUnit}`;
                            },
                        },
                        backgroundColor: '#eff6ff',
                        titleFont: {
                            size: 13,
                        },
                        titleColor: '#6783b8',
                        titleMarginBottom: 6,
                        bodyColor: '#9eaecf',
                        bodyFont: {
                            size: 12
                        },
                        bodySpacing: 4,
                        padding: 10,
                        footerMarginTop: 0,
                        displayColors: false
                    },
                },
                rotation: 1,
                cutoutPercentage: 40,
                maintainAspectRatio: false,
            }
        });
    })
}

getProjectInvestmentSum().then(res => {
    doughnutChart();
});
