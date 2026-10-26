import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { BASE_STATION_TECHNOLOGY_OPTIONS, BASE_STATION_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { BaseStationFilter } from './types';

interface Props {
  value: BaseStationFilter;
  onChange: (next: BaseStationFilter) => void;
  onReset: () => void;
}

export function BaseStationFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: BaseStationFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Base Station filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Base Station"
          placeholder="code, name or description"
          value={value.q ?? ''}
          onChange={(v) => patch({ q: v || undefined })}
        />
      </FilterField>
      <FilterField label="Status">
        <MultiSelect
          ariaLabel="Status filter"
          options={LIFECYCLE_OPTIONS}
          value={value.status ?? []}
          onChange={(v) => patch({ status: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Code">
        <Input
          aria-label="Code filter"
          value={value.code ?? ''}
          onChange={(v) => patch({ code: v || undefined })}
        />
      </FilterField>
      <FilterField label="Name">
        <Input
          aria-label="Name filter"
          value={value.name ?? ''}
          onChange={(v) => patch({ name: v || undefined })}
        />
      </FilterField>
      <FilterField label="External Ref">
        <Input
          aria-label="External Ref filter"
          value={value.externalRef ?? ''}
          onChange={(v) => patch({ externalRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Site Id">
        <Input
          aria-label="Site Id filter"
          placeholder="UUID"
          value={value.siteId ?? ''}
          onChange={(v) => patch({ siteId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Enb Id">
        <RangeInputs
          ariaLabel="Enb Id"
          min={value.enbIdMin}
          max={value.enbIdMax}
          onChange={(min, max) => patch({ enbIdMin: min, enbIdMax: max })}
        />
      </FilterField>
      <FilterField label="Technology">
        <MultiSelect
          ariaLabel="Technology filter"
          options={BASE_STATION_TECHNOLOGY_OPTIONS}
          value={value.technology ?? []}
          onChange={(v) => patch({ technology: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Sector Count">
        <RangeInputs
          ariaLabel="Sector Count"
          min={value.sectorCountMin}
          max={value.sectorCountMax}
          onChange={(min, max) => patch({ sectorCountMin: min, sectorCountMax: max })}
        />
      </FilterField>
      <FilterField label="Cell Count">
        <RangeInputs
          ariaLabel="Cell Count"
          min={value.cellCountMin}
          max={value.cellCountMax}
          onChange={(min, max) => patch({ cellCountMin: min, cellCountMax: max })}
        />
      </FilterField>
      <FilterField label="Labels">
        <Input
          aria-label="Labels filter"
          value={value.labels ?? ''}
          onChange={(v) => patch({ labels: v || undefined })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={BASE_STATION_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Source System">
        <Input
          aria-label="Source System filter"
          value={value.sourceSystem ?? ''}
          onChange={(v) => patch({ sourceSystem: v || undefined })}
        />
      </FilterField>
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
        />
      </FilterField>
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
        />
      </FilterField>
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
