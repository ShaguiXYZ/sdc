import { Injectable } from '@angular/core';
import { Observable, Subject, firstValueFrom, map, of } from 'rxjs';
import { IComponentModel } from 'src/app/core/models/sdc';
import { ComponentService, DataListService } from 'src/app/core/services/sdc';

@Injectable()
export class ComponentFormService {
  private emptyObservableFn = () => of([]);
  private dynamicBackendOptions?: { [key: string]: (term: string) => Observable<string[]> };
  private data$: Subject<Partial<IComponentModel>> = new Subject();

  constructor(
    private readonly dataListService: DataListService,
    private readonly componentService: ComponentService
  ) {
    this.dataListValues().then(data => (this.dynamicBackendOptions = data));
  }

  public onDataChange(): Observable<Partial<IComponentModel>> {
    return this.data$.asObservable();
  }

  public dataListByName(name: string): (key: string) => Observable<string[]> {
    return this.dynamicBackendOptions?.[name] ?? this.emptyObservableFn;
  }

  public getDataList(name: string): Promise<string[]> {
    return firstValueFrom(this.dataListService.dataListValues(name));
  }

  public getComponent(id: number): void {
    this.componentService.component(id).then(data => this.data$.next(data));
  }

  public saveComponent(value: any): void {
    console.log('saveComponent', value);
  }

  private dataListValues = async (): Promise<{ [datalist: string]: (key: string) => Observable<string[]> }> => {
    const data = await this.dataListService.availableDataLists();
    const result: { [datalist: string]: (key: string) => Observable<string[]> } = {};

    data.forEach(
      item => (result[item] = (key: string) => this.dataListService.dataListValues(item).pipe(map(x => x.filter(y => y.includes(key)))))
    );

    return result;
  };
}
