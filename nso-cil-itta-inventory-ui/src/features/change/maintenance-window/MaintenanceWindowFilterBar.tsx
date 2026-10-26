import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { MAINTENANCE_WINDOW_IMPACT_LEVEL_OPTIONS, MAINTENANCE_WINDOW_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { MaintenanceWindowFilter } from './types';

interface Props {
  value: MaintenanceWindowFilter;
  onChange: (next: MaintenanceWindowFilter) => void;
  onReset: () => void;
}

export function MaintenanceWindowFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: MaintenanceWindowFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Maintenance Window filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Maintenance Window"
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
      <FilterField label="Window Name">
        <Input
          aria-label="Window Name filter"
          value={value.windowName ?? ''}
          onChange={(v) => patch({ windowName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Starts At">
        <DateRange
          ariaLabel="Starts At"
          from={value.startsAtFrom}
          to={value.startsAtTo}
          onChange={(from, to) => patch({ startsAtFrom: from, startsAtTo: to })}
        />
      </FilterField>
      <FilterField label="Ends At">
        <DateRange
          ariaLabel="Ends At"
          from={value.endsAtFrom}
          to={value.endsAtTo}
          onChange={(from, to) => patch({ endsAtFrom: from, endsAtTo: to })}
        />
      </FilterField>
      <FilterField label="Scope">
        <Input
          aria-label="Scope filter"
          value={value.scope ?? ''}
          onChange={(v) => patch({ scope: v || undefined })}
        />
      </FilterField>
      <FilterField label="Impact Level">
        <MultiSelect
          ariaLabel="Impact Level filter"
          options={MAINTENANCE_WINDOW_IMPACT_LEVEL_OPTIONS}
          value={value.impactLevel ?? []}
          onChange={(v) => patch({ impactLevel: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Effective To">
        <DateRange
          ariaLabel="Effective To"
          from={value.effectiveToFrom}
          to={value.effectiveToTo}
          onChange={(from, to) => patch({ effectiveToFrom: from, effectiveToTo: to })}
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
          options={MAINTENANCE_WINDOW_LIFECYCLE_STAGE_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
