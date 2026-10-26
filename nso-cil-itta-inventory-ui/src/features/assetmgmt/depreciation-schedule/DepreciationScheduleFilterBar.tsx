import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { DEPRECIATION_SCHEDULE_METHOD_OPTIONS, DEPRECIATION_SCHEDULE_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { DepreciationScheduleFilter } from './types';

interface Props {
  value: DepreciationScheduleFilter;
  onChange: (next: DepreciationScheduleFilter) => void;
  onReset: () => void;
}

export function DepreciationScheduleFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: DepreciationScheduleFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Depreciation Schedule filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Depreciation Schedule"
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
      <FilterField label="Asset Id">
        <Input
          aria-label="Asset Id filter"
          placeholder="UUID"
          value={value.assetId ?? ''}
          onChange={(v) => patch({ assetId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Method">
        <MultiSelect
          ariaLabel="Method filter"
          options={DEPRECIATION_SCHEDULE_METHOD_OPTIONS}
          value={value.method ?? []}
          onChange={(v) => patch({ method: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Useful Life Months">
        <RangeInputs
          ariaLabel="Useful Life Months"
          min={value.usefulLifeMonthsMin}
          max={value.usefulLifeMonthsMax}
          onChange={(min, max) => patch({ usefulLifeMonthsMin: min, usefulLifeMonthsMax: max })}
        />
      </FilterField>
      <FilterField label="Salvage Value">
        <RangeInputs
          ariaLabel="Salvage Value"
          min={value.salvageValueMin}
          max={value.salvageValueMax}
          onChange={(min, max) => patch({ salvageValueMin: min, salvageValueMax: max })}
        />
      </FilterField>
      <FilterField label="Start Date">
        <DateRange
          ariaLabel="Start Date"
          from={value.startDateFrom}
          to={value.startDateTo}
          onChange={(from, to) => patch({ startDateFrom: from, startDateTo: to })}
        />
      </FilterField>
      <FilterField label="Effective From">
        <DateRange
          ariaLabel="Effective From"
          from={value.effectiveFromFrom}
          to={value.effectiveFromTo}
          onChange={(from, to) => patch({ effectiveFromFrom: from, effectiveFromTo: to })}
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
          options={DEPRECIATION_SCHEDULE_LIFECYCLE_STAGE_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
