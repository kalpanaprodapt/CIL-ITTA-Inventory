import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { K8S_CLUSTER_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { K8sClusterFilter } from './types';

interface Props {
  value: K8sClusterFilter;
  onChange: (next: K8sClusterFilter) => void;
  onReset: () => void;
}

export function K8sClusterFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: K8sClusterFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="K8s Cluster filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search K8s Cluster"
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
      <FilterField label="Cluster Name">
        <Input
          aria-label="Cluster Name filter"
          value={value.clusterName ?? ''}
          onChange={(v) => patch({ clusterName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Cluster Version">
        <Input
          aria-label="Cluster Version filter"
          value={value.clusterVersion ?? ''}
          onChange={(v) => patch({ clusterVersion: v || undefined })}
        />
      </FilterField>
      <FilterField label="Node Count">
        <RangeInputs
          ariaLabel="Node Count"
          min={value.nodeCountMin}
          max={value.nodeCountMax}
          onChange={(min, max) => patch({ nodeCountMin: min, nodeCountMax: max })}
        />
      </FilterField>
      <FilterField label="Cloud Account Id">
        <Input
          aria-label="Cloud Account Id filter"
          placeholder="UUID"
          value={value.cloudAccountId ?? ''}
          onChange={(v) => patch({ cloudAccountId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
        />
      </FilterField>
      <FilterField label="Api Endpoint">
        <Input
          aria-label="Api Endpoint filter"
          value={value.apiEndpoint ?? ''}
          onChange={(v) => patch({ apiEndpoint: v || undefined })}
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
          options={K8S_CLUSTER_LIFECYCLE_STAGE_OPTIONS}
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
