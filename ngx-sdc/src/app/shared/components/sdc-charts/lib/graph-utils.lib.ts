import { DataInfo } from 'src/app/core/models';
import { LegendPosition } from '../models';

export const legendPosition = (position?: LegendPosition) =>
  position
    ? {
        ['bottom']: {
          grid: {
            left: '2%',
            right: '2%',
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
            right: '2%',
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
            left: '2%',
            right: '2%',
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
            left: '2%',
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
          left: '2%',
          right: '2%',
          bottom: '5%',
          top: '5%',
          containLabel: true
        }
      };

export const stringGraphToRecord = (data: string = ''): DataInfo<number> => {
  const record: DataInfo<number> = {};

  data
    .split(';')
    .filter(value => /([^=]+)=(\d+)(.?(\d+))?/.test(value))
    .forEach(eq => {
      const [key, ...value] = eq.split('=');
      record[key] = Number(value[0]);
    });

  return record;
};
