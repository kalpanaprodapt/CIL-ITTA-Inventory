import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { THRESHOLD_POLICY_CRITICALITY_OPTIONS } from './types';
import type { ThresholdPolicyFilter } from './types';

interface Props {
  value: ThresholdPolicyFilter;
  onChange: (next: ThresholdPolicyFilter) => void;
  onReset: () => void;
}

export function ThresholdPolicyFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: ThresholdPolicyFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Threshold Policy filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Threshold Policy"
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
      <FilterField label="Policy Name">
        <Input
          aria-label="Policy Name filter"
          value={value.policyName ?? ''}
          onChange={(v) => patch({ policyName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Metric">
        <Input
          aria-label="Metric filter"
          value={value.metric ?? ''}
          onChange={(v) => patch({ metric: v || undefined })}
        />
      </FilterField>
      <FilterField label="Warn Pct">
        <RangeInputs
          ariaLabel="Warn Pct"
          min={value.warnPctMin}
          max={value.warnPctMax}
          onChange={(min, max) => patch({ warnPctMin: min, warnPctMax: max })}
        />
      </FilterField>
      <FilterField label="Crit Pct">
        <RangeInputs
          ariaLabel="Crit Pct"
          min={value.critPctMin}
          max={value.critPctMax}
          onChange={(min, max) => patch({ critPctMin: min, critPctMax: max })}
        />
      </FilterField>
      <FilterField label="Evaluation Window Minutes">
        <RangeInputs
          ariaLabel="Evaluation Window Minutes"
          min={value.evaluationWindowMinutesMin}
          max={value.evaluationWindowMinutesMax}
          onChange={(min, max) => patch({ evaluationWindowMinutesMin: min, evaluationWindowMinutesMax: max })}
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
          options={THRESHOLD_POLICY_CRITICALITY_OPTIONS}
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
