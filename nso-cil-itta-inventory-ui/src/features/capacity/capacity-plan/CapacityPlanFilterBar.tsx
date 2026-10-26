import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { CAPACITY_PLAN_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { CapacityPlanFilter } from './types';

interface Props {
  value: CapacityPlanFilter;
  onChange: (next: CapacityPlanFilter) => void;
  onReset: () => void;
}

export function CapacityPlanFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: CapacityPlanFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Capacity Plan filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Capacity Plan"
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
      <FilterField label="Plan Name">
        <Input
          aria-label="Plan Name filter"
          value={value.planName ?? ''}
          onChange={(v) => patch({ planName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Horizon Months">
        <RangeInputs
          ariaLabel="Horizon Months"
          min={value.horizonMonthsMin}
          max={value.horizonMonthsMax}
          onChange={(min, max) => patch({ horizonMonthsMin: min, horizonMonthsMax: max })}
        />
      </FilterField>
      <FilterField label="Target Utilization Pct">
        <RangeInputs
          ariaLabel="Target Utilization Pct"
          min={value.targetUtilizationPctMin}
          max={value.targetUtilizationPctMax}
          onChange={(min, max) => patch({ targetUtilizationPctMin: min, targetUtilizationPctMax: max })}
        />
      </FilterField>
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
        />
      </FilterField>
      <FilterField label="Approved On">
        <DateRange
          ariaLabel="Approved On"
          from={value.approvedOnFrom}
          to={value.approvedOnTo}
          onChange={(from, to) => patch({ approvedOnFrom: from, approvedOnTo: to })}
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
          options={CAPACITY_PLAN_LIFECYCLE_STAGE_OPTIONS}
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
