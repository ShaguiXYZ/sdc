import { LegendPosition } from '../models';

export const legendPosition = (position?: LegendPosition) =>
  position
    ? {
        ['bottom']: {
          grid: {
            left: '5%',
            right: '5%',
            bottom: '10%',
            top: '5%',
            containLabel: true
          },
          legend: {
            bottom: -5,
            orient: 'horizontal'
          }
        },
        ['left']: {
          grid: {
            right: '5%',
            bottom: '5%',
            top: '5%',
            containLabel: true
          },
          legend: {
            orient: 'vertical',
            left: 10,
            top: 'center'
          }
        },
        ['top']: {
          grid: {
            left: '5%',
            right: '5%',
            bottom: '5%',
            top: '10%',
            containLabel: true
          },
          legend: {
            orient: 'horizontal'
          }
        },
        ['right']: {
          grid: {
            left: '5%',
            bottom: '5%',
            top: '5%',
            containLabel: true
          },
          legend: {
            orient: 'vertical',
            right: 10,
            top: 'center'
          }
        }
      }[position]
    : {
        grid: {
          left: '5%',
          right: '5%',
          bottom: '5%',
          top: '5%',
          containLabel: true
        }
      };
