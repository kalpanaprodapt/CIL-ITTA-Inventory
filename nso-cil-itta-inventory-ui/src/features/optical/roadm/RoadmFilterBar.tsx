import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { ROADM_CRITICALITY_OPTIONS } from './types';
import type { RoadmFilter } from './types';

interface Props {
  value: RoadmFilter;
  onChange: (next: RoadmFilter) => void;
  onReset: () => void;
}

export function RoadmFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: RoadmFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Roadm filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Roadm"
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
      <FilterField label="System Id">
        <Input
          aria-label="System Id filter"
          placeholder="UUID"
          value={value.systemId ?? ''}
          onChange={(v) => patch({ systemId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Degrees">
        <RangeInputs
          ariaLabel="Degrees"
          min={value.degreesMin}
          max={value.degreesMax}
          onChange={(min, max) => patch({ degreesMin: min, degreesMax: max })}
        />
      </FilterField>
      <FilterField label="Colorless">
        <TriState
          ariaLabel="Colorless filter"
          value={value.colorless}
          onChange={(v) => patch({ colorless: v })}
        />
      </FilterField>
      <FilterField label="Directionless">
        <TriState
          ariaLabel="Directionless filter"
          value={value.directionless}
          onChange={(v) => patch({ directionless: v })}
        />
      </FilterField>
      <FilterField label="Contentionless">
        <TriState
          ariaLabel="Contentionless filter"
          value={value.contentionless}
          onChange={(v) => patch({ contentionless: v })}
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
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={ROADM_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Cost Center">
        <Input
          aria-label="Cost Center filter"
          value={value.costCenter ?? ''}
          onChange={(v) => patch({ costCenter: v || undefined })}
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
