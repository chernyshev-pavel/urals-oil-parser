<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>График цен нефти «Юралс»</title>

    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.3"></script>
</head>
<body>

<div style="width: 100%">
    <canvas id="chart"></canvas>
</div>

<script>
    new Chart(document.getElementById('chart').getContext('2d'), {
        type: 'line',

        data: {
            labels: [
                <#list entries as entry>
                '${entry.start}',
                </#list>
            ],
            datasets: [{
                label: 'Средняя цена на нефть сырую марки «Юралс»',
                borderColor: 'rgb(19,42,128)',
                background: 'rgb(19,42,128)',
                data: [
                    <#list entries as entry>
                    ${entry.averagePrice?c},
                    </#list>
                ],
                showLine: true
            }]
        }
    });
</script>

</body>
</html>