import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { QOS_POLICY_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { QosPolicyFilter } from './types';

interface Props {
  value: QosPolicyFilter;
  onChange: (next: QosPolicyFilter) => void;
  onReset: () => void;
}

export function QosPolicyFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: QosPolicyFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Qos Policy filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Qos Policy"
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
      <FilterField label="Class Count">
        <RangeInputs
          ariaLabel="Class Count"
          min={value.classCountMin}
          max={value.classCountMax}
          onChange={(min, max) => patch({ classCountMin: min, classCountMax: max })}
        />
      </FilterField>
      <FilterField label="Cir Mbps">
        <RangeInputs
          ariaLabel="Cir Mbps"
          min={value.cirMbpsMin}
          max={value.cirMbpsMax}
          onChange={(min, max) => patch({ cirMbpsMin: min, cirMbpsMax: max })}
        />
      </FilterField>
      <FilterField label="Pir Mbps">
        <RangeInputs
          ariaLabel="Pir Mbps"
          min={value.pirMbpsMin}
          max={value.pirMbpsMax}
          onChange={(min, max) => patch({ pirMbpsMin: min, pirMbpsMax: max })}
        />
      </FilterField>
      <FilterField label="Marking Scheme">
        <Input
          aria-label="Marking Scheme filter"
          value={value.markingScheme ?? ''}
          onChange={(v) => patch({ markingScheme: v || undefined })}
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
          options={QOS_POLICY_LIFECYCLE_STAGE_OPTIONS}
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
